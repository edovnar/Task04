package sb.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sb.domain.entity.LineItem;
import sb.domain.entity.Order;
import sb.domain.entity.User;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.UserDAO;
import sb.service.exception.UserNotFoundException;

import java.util.List;

@Service
public class OrderService {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;

    public OrderService(OrderDAO orderDAO, UserDAO userDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    public Order get(int id) {
        return orderDAO.get(id);
    }

    public List<Order> getByUser(int id) {
        return orderDAO.getByUser(id);
    }

    public String save(List<LineItem> lineItems) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException("No user"));

        String response = "Check next products: ";
        int k = 0;
        for (LineItem lineItem : lineItems) {
            if (productDAO.get(lineItem.getProductId()).isEmpty()) {
                response = response.concat(lineItem.getProductId() + " ");
                k++;
            }
        }
        if (k == lineItems.size()) {
            int orderId = orderDAO.post(user);
            for (LineItem lineItem : lineItems) {
                if (productDAO.get(lineItem.getProductId()).isPresent()) {
                    lineItem.setOrderId(orderId);
                }
            }
        } else {
            return response;
        }
        return "Order is accepted";
    }

    public void updateStatus(String status, int id) {
        orderDAO.update(status, id);
    }

    public  void delete(int id) {
        orderDAO.delete(id);
    }
}
