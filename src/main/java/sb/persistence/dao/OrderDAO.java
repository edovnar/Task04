package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Order;
import sb.domain.entity.User;

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

    private final String SQL_SELECT_ALL = "Select * from orders";

    private final String SQL_SELECT_BY_ID = "Select * from orders where id = :id";

    private final String SQL_SELECT_BY_USER = "Select * from orders where submitted_by = :submittedBy";

    private final String SQL_INSERT =
            "Insert into orders(shipped, submitted_by, submitted_at, updated_at) values (:shipped, :submittedBy, :submittedAt, :updatedAt)";

    private final String SQL_DELETE_BY_ID = "Delete from orders where id = :id";

    private final String SQL_UPDATE_STATUS_BY_ID = "Update orders set shipped = :shipped, updated_at = :updatedAt where id = :id";


    public List<Order> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Order get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map,rowMapper);
    }

    public List<Order> getByUser(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("submittedBy", id);

        return namedJdbcTemplate.query(SQL_SELECT_BY_USER, map, rowMapper);
    }

    public void update(String status, int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("shipped", status)
                .addValue("updatedAt", new Date())
                .addValue("id", id);
       namedJdbcTemplate.update(SQL_UPDATE_STATUS_BY_ID, map);
    }

    public int post(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("shipped", false)
                .addValue("submittedBy", user.getId())
                .addValue("submittedAt", new Date())
                .addValue("updatedAt", null);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(SQL_INSERT, map, keyHolder, new String[] {"id"});
        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
       namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
