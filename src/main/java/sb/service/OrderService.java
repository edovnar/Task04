package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.entity.*;
import sb.persistence.dao.*;
import sb.service.exception.BadOrderException;
import sb.service.exception.CreationException;
import sb.service.exception.NotFoundException;

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
        return orderDAO.getAll();
    }

    public Order get(int id) {
        return orderDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such order"));
    }

    public List<Order> getByUser(int id) {
        return orderDAO.getByUser(id);
    }

    public void save(List<LineItem> lineItems) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("No user"));

        Optional<Product> product;
        Optional<Stock> stock;
        int orderId = orderDAO.post(user);

        for(LineItem lineItem : lineItems) {

            product = productDAO.get(lineItem.getProductId());
            stock = stockDAO.get(lineItem.getProductId());

            if(product.isEmpty() || stock.isEmpty()){
                throw new BadOrderException(lineItem, "This product doesn't exists");
            }

            int productQuantity = lineItem.getQuantity();
            int stockQuantity = stockDAO.get(lineItem.getProductId()).get().getQuantity();

            if(productQuantity > stockQuantity) {
                throw new BadOrderException(lineItem, "Don't have so many on the stock(" + stockQuantity +")");
            }

            stock.get().setQuantity(stockQuantity-productQuantity);
            stockDAO.update(stock.get());
            lineItem.setOrderId(orderId);
            lineItemDAO.post(lineItem);
        }
    }

    public void updateStatus(String status, int id) {

        orderDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such order"));

        if(status!=null && (status.equals("false") || status.equals("true"))){
            orderDAO.update(status, id);
        }else throw new CreationException("Wrong status");
    }

    public void update(int orderId, LineItem lineItem) {
        if(orderDAO.get(orderId).isPresent()
                && orderDAO.get(orderId).get().getShipped().equals("false")) {
            Stock stock = stockDAO.get(lineItem.getProductId())
                    .orElseThrow(() -> new NotFoundException("No such product"));

            int newQuantity = lineItem.getQuantity();
            int oldQuantity = lineItemDAO.get(orderId, lineItem.getProductId()).getQuantity();
            int stockQuantity = stockDAO.get(lineItem.getProductId()).get().getQuantity();

            if(stockQuantity + oldQuantity >= newQuantity){
                stock.setQuantity(stockQuantity + Math.abs(newQuantity - oldQuantity));
                stockDAO.update(stock);

                lineItem.setOrderId(orderId);
                lineItemDAO.update(lineItem);
            }
        } else throw new NotFoundException("Can't update such order");
    }

    public  void delete(int id) {
        orderDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        orderDAO.delete(id);
    }
}
