package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.Order;

@Component
interface OrderRepo extends JpaRepository<Order, Integer> {
}
