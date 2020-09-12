package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sb.domain.entity.*;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.UserDAO;
import sb.service.exception.BadOrderException;
import sb.service.exception.StockNotFoundException;
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

        Optional<Product> product;
        Optional<Stock> stock;
        for(LineItem lineItem : lineItems) {

            product = productDAO.get(lineItem.getProductId());
            stock = stockDAO.get(lineItem.getProductId());

            if(product.isEmpty() || stock.isEmpty()){
                throw new BadOrderException(lineItem);
            }

            int productQuantity = lineItem.getQuantity();
            int stockQuantity = stockDAO.get(lineItem.getProductId()).get().getQuantity();

            if(productQuantity < stockQuantity) {
                throw new BadOrderException("Stock has less products than you need");
            }
        }
        orderDAO.post(user);
    }

    public void updateStatus(String status, int id) {
        orderDAO.update(status, id);
    }

    public  void delete(int id) {
        orderDAO.delete(id);
    }
}
