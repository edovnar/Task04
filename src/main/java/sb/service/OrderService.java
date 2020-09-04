package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.Order;
import sb.persistence.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService extends GeneralService<Order> {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    public List<Order> getByStatus(String status){

        if(status == null){
            return jpaRepo.findAll();
        }
        return orderRepository.getByStatus(status);
    }
}
