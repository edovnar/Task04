package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sb.domain.entity.LineItem;
import sb.domain.entity.Order;
import sb.domain.entity.Product;
import sb.domain.entity.User;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.UserDAO;
import sb.service.exception.BadOrderException;
import sb.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;
    @Autowired
    private StockDAO stockDAO;

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

    public void save(List<LineItem> lineItems) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userDAO.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException("No user"));

        orderDAO.post(user);

        for(LineItem lineItem : lineItems) {
            Optional<Product> product = productDAO.get(lineItem.getProductId());
            int quantityProduct = lineItem.getQuantity();
            int quantityStock = stockDAO.get(lineItem.getProductId()).getQuantity();

            if(product.isEmpty() || quantityProduct < quantityStock) {
                throw new BadOrderException(lineItem);
            }
        }
    }

    public void updateStatus(String status, int id) {
        orderDAO.update(status, id);
    }

    public  void delete(int id) {
        orderDAO.delete(id);
    }
}
