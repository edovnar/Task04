package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.Order;

@Component
public class OrderService extends Service<Order> {

    public OrderService(JpaRepository<Order, Integer> jpaRepo) {
        super(jpaRepo);
    }
}
