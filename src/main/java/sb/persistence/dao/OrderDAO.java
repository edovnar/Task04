package sb.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sb.domain.Order;

import java.util.List;

@Component
public class OrderDAO {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Order> getAll() {
        return  jdbc.query(
                "select * from orders join lineItems" +
                        "on orders.id = ordersid",
                new BeanPropertyRowMapper<>(Order.class)
        );
    }

    public Order get(int id){
        return jdbc.query(
                "select * from orders join lineItems " +
                        "on orders.id = orderid " +
                        "where orders.id = ?",
                new Object[] {id},
                new BeanPropertyRowMapper<>(Order.class)
        ).stream().findAny().orElse(null);
    }
}
