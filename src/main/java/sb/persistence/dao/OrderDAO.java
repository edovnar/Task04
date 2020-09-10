package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Order;

import java.util.Date;
import java.util.List;

@Repository
public class OrderDAO{
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Order> rowMapper;

    public OrderDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Order.class);
    }

    private final String GET_ALL = "Select * from orders";

    private final String GET = "Select * from orders where id = :id";

    private final String GET_BY_USER = "Select * from orders where submitted_by = :submittedBy";

    private final String POST =
            "Insert into orders values (:id, :shipped, :submittedBy, :submittedAt, :updatedAt)";

    private final String DELETE = "Delete from orders where id = :id";

    private final String UPDATE_STATUS = "Update orders set shipped = :shipped, updated_at = :updatedAt where id = :id";


    public List<Order> getAll() {
        return namedJdbcTemplate.query(GET_ALL, rowMapper);
    }

    public Order get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(GET, map,rowMapper);
    }

    public List<Order> getByUser(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("submittedBy", id);

        return namedJdbcTemplate.query( GET_BY_USER, map, rowMapper);
    }

    public void update(String status, int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("shipped", status)
                .addValue("updatedAt", new Date())
                .addValue("id", id);
       namedJdbcTemplate.update(UPDATE_STATUS, map);
    }

    public void post(Order order) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("shipped", false)
                .addValue("submittedBy", order.getSubmittedBy())
                .addValue("submittedAt", new Date())
                .addValue("updatedAt", null);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(POST, map);
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
       namedJdbcTemplate.update(DELETE, map);
    }
}
