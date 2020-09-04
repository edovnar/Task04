package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sb.domain.Order;
import sb.persistence.query.OrderQuery;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(OrderQuery.GET_BY_STATUS)
    List<Order> getByStatus(@Param("status") boolean status);
}
