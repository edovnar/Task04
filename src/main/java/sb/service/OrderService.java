package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import sb.domain.Order;
import sb.persistence.dao.OrderDAO;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    public Order get(int id) {
        return orderDAO.get(id);
    }

    public void save(Order order) {
        orderDAO.post(
                order.getId(),
                order.getSubmittedBy()
        );
    }

    public void updateStatus(String status, int id) {
        orderDAO.update(status, id);
    }

    public  void delete(int id) {
        orderDAO.delete(id);
    }
}
