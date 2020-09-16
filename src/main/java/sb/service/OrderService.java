package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.dto.request.OrderDTORequest;
import sb.domain.entity.*;
import sb.persistence.dao.*;
import sb.service.exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;
    private StockDAO stockDAO;
    private LineItemDAO lineItemDAO;


    public OrderService(OrderDAO orderDAO, UserDAO userDAO, ProductDAO productDAO, StockDAO stockDAO, LineItemDAO lineItemDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.stockDAO = stockDAO;
        this.lineItemDAO = lineItemDAO;
    }


    public List<Order> getAll() {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("User is not found"));

        if(user.getRole().equals("admin")) {
            return orderDAO.getAll();
        } else {
            return orderDAO.getByUser(user.getId());
        }
    }


    public Order get(int id) {
        return orderDAO.get(id).orElseThrow(
                () -> new NotFoundException("No such order")
        );
    }

    public List<Order> getByUser(int id) {
        return orderDAO.getByUser(id);
    }


    public Order save(OrderDTORequest orderDTORequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("No such user")
        );

        List<LineItem> lineItems = orderDTORequest.getLineItems();

        Optional<Product> product;
        Optional<Stock> stock;

        int orderId = orderDAO.post(user);

        List<LineItem> lineItemSet = new ArrayList<>();

        for(LineItem lineItem : lineItems) {

            int productId = lineItem.getProductId();

            product = productDAO.get(productId);

            if(product.isEmpty()){
                throw new BadOrderException(lineItem, "This product doesn't exists");
            }

            stock = stockDAO.get(productId);

            int productQuantity = lineItem.getQuantity();
            int stockQuantity = stockDAO.get(lineItem.getProductId()).get().getQuantity();

            if(productQuantity < 1 ){
                throw  new BadOrderException(lineItem, "Quantity should be positive");
            }

            if(productQuantity > stockQuantity) {
                throw new BadOrderException(lineItem,
                        "Don't have so many on the stock(" + stockQuantity +")"
                );
            }

            stock.get().setQuantity(stockQuantity - productQuantity);
            stockDAO.update(stock.get());

            if(!lineItemSet.contains(lineItem)) {
                lineItemSet.add(lineItem);

                lineItem.setOrderId(orderId);
                lineItemDAO.post(lineItem);
            } else {
                int index = lineItemSet.indexOf(lineItem);

                lineItemSet.get(index).setQuantity(
                        lineItemSet.get(index).getQuantity() + lineItem.getQuantity()
                );
                lineItemDAO.update(lineItemSet.get(index));
            }
        }
        return orderDAO.get(orderId).get();
    }


    public Order updateStatus(String status, int orderId) {

        orderDAO.get(orderId).orElseThrow(
                () -> new NotFoundException("No such order")
        );

        if(status != null && (status.equals("shipped") || status.equals("unshipped"))){
            orderDAO.update(status, orderId);
        } else {
            throw new CreationException("Wrong status");
        }

        return orderDAO.get(orderId).get();
    }


    public Order update(int orderId, OrderDTORequest orderDTORequest) {

        if(orderDAO.get(orderId).isEmpty()) {
            throw new NotFoundException("No such order");
        }

        if(orderDAO.get(orderId).get().getStatus().equals("shipped")) {
            throw new NotFoundException("Can't update shipped order");
        }

        for (LineItem lineItem : lineItemDAO.getByOrder(orderId)) {

            int productId = lineItem.getProductId();
            Stock stock = stockDAO.get(productId).get();
            int stockQuantity = stock.getQuantity();

            stock.setQuantity(stockQuantity + lineItem.getQuantity());
            stockDAO.update(stock);
        }

        lineItemDAO.deleteByOrder(orderId);

        for (LineItem lineItem : orderDTORequest.getLineItems()) {

            Integer productId = lineItem.getProductId();

            if(productId == null) {
                throw new BadOrderException(lineItem, "Specify product");
            }

            if(productDAO.get(productId).isEmpty()) {
                throw new BadOrderException(lineItem, "No such product");
            }

            Stock stock = stockDAO.get(productId).get();

            int productQuantity = lineItem.getQuantity();
            int stockQuantity = stock.getQuantity();

            if(productQuantity < 1) {
                throw new BadOrderException(lineItem, "Quantity should be positive");
            }

            if(stockQuantity < productQuantity) {
                throw new BadOrderException(lineItem, "Don't have so many on the stock");
            }

            stock.setQuantity(
                    stockQuantity - productQuantity
            );
            stockDAO.update(stock);

            lineItem.setOrderId(orderId);
            lineItemDAO.post(lineItem);
        }

        return orderDAO.get(orderId).get();
    }


    public void delete(int id) {
        orderDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        lineItemDAO.deleteByOrder(id);
        orderDAO.delete(id);
    }
}
