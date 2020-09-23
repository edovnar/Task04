package sb.service;

import org.springframework.data.domain.Pageable;
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

    public List<Order> getAll(Pageable pageable) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("User is not found"));

        if(user.getRole().equals("admin")) {
            return orderDAO.getAll(pageable);
        } else {
            return orderDAO.getByUserId(user.getId());
        }
    }

    public Order get(int id) {
        return orderDAO.get(id).orElseThrow(
                () -> new NotFoundException("No such order")
        );
    }

    @Transactional
    public Order create(OrderDTORequest orderDTORequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("No such user")
        );

        int orderId = orderDAO.create(user);

        return save(orderId, orderDTORequest);
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

    @Transactional
    public Order update(int orderId, OrderDTORequest orderDTORequest) {

        if(orderDAO.get(orderId).isEmpty()) {
            throw new NotFoundException("No such order");
        }

        if(orderDAO.get(orderId).get().getStatus().equals("shipped")) {
            throw new NotFoundException("Can't update shipped order");
        }

        for (LineItem lineItem : lineItemDAO.getByOrderId(orderId)) {

            int productId = lineItem.getProductId();
            Stock stock = stockDAO.get(productId).get();
            int stockQuantity = stock.getQuantity();

            stock.setQuantity(stockQuantity + lineItem.getQuantity());
            stockDAO.update(stock);
        }

        lineItemDAO.deleteByOrder(orderId);

        return save(orderId, orderDTORequest);
    }

    public void delete(int id) {
        orderDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        lineItemDAO.deleteByOrder(id);
        orderDAO.delete(id);
    }

    public Order save(int orderId, OrderDTORequest orderDTORequest) {
        List<LineItem> lineItems = orderDTORequest.getLineItems();

        Optional<Product> product;
        Stock stock;

        List<LineItem> lineItemOrigin = new ArrayList<>();

        for(LineItem lineItem : lineItems) {

            int productId = lineItem.getProductId();

            product = productDAO.get(productId);

            if(product.isEmpty()){
                throw new BadOrderException(lineItem, "This product doesn't exists");
            }

            stock = stockDAO.get(productId).get();

            int productQuantity = lineItem.getQuantity();
            int stockQuantity = stock.getQuantity();

            if(productQuantity < 1) {
                throw  new BadOrderException(lineItem, "Quantity should be positive");
            }

            if(productQuantity > stockQuantity) {
                throw new BadOrderException(lineItem,
                        "Don't have so many on the stock(" + stockQuantity +")"
                );
            }

            stock.setQuantity(stockQuantity - productQuantity);
            stockDAO.update(stock);

            if(!lineItemOrigin.contains(lineItem)) {
                lineItemOrigin.add(lineItem);

                lineItem.setOrderId(orderId);
                lineItemDAO.create(lineItem);
            } else {
                int index = lineItemOrigin.indexOf(lineItem);

                lineItemOrigin.get(index).setQuantity(
                        lineItemOrigin.get(index).getQuantity() + lineItem.getQuantity()
                );
                lineItemDAO.update(lineItemOrigin.get(index));
            }
        }

        return orderDAO.get(orderId).get();
    }
}
