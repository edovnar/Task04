package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.Order;
import sb.persistence.repository.OrderRepository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class OrderService extends GeneralService<Order> {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllByStatus(boolean status){
        return orderRepository.getByStatus(status);
    }

}