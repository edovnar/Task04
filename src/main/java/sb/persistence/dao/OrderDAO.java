package sb.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Order;
import sb.domain.entity.User;
import sb.utils.PaginationUtil;

import java.util.*;

@Repository
public class OrderDAO {
    
    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Order> ROW_MAPPER;
    private final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);

    public OrderDAO(NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE) {
        this.NAMED_JDBC_TEMPLATE = NAMED_JDBC_TEMPLATE;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Order.class);
    }

    private final static String SQL_SELECT_ALL = "select id, status, submitted_by, submitted_at, updated_at " +
                                            "from orders";

    private final static String SQL_SELECT_BY_ORDER_ID = "select id, status, submitted_by, submitted_at, updated_at " +
                                            "from orders where id = :id";

    private final static String SQL_SELECT_BY_USER_ID = "select id, status, submitted_by, submitted_at, updated_at " +
                                                "from orders where submitted_by = :submittedBy";

    private final static String SQL_INSERT = "insert into orders(status, submitted_by, submitted_at, updated_at) " +
                                        "values (:status, :submittedBy, :submittedAt, :updatedAt)";

    private final static String SQL_DELETE_BY_ORDER_ID = "delete from orders where id = :id";

    private final static String SQL_UPDATE_STATUS_BY_ORDER_ID = "update orders set status = :status, updated_at = :updatedAt " +
                                                    "where id = :id";

    public List<Order> getAll(Pageable pageable) {

        String query = PaginationUtil.addPaging(SQL_SELECT_ALL, pageable);

        try {
            return NAMED_JDBC_TEMPLATE.query(query, ROW_MAPPER);
        } catch (BadSqlGrammarException e) {
            LOG.info(e.getMessage());

            return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
        }
    }

    public Optional<Order> get(int orderId) {
        Map<String, Integer> parameterMap = Map.of("id", orderId);

        Order order = null;
        try {
            order = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_ORDER_ID, parameterMap, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            Logger log = LoggerFactory.getLogger(OrderDAO.class);
            log.info(e.getMessage());
        }

        return Optional.ofNullable(order);
    }

    public List<Order> getByUserId(int userId) {
        Map<String, Integer> parameterMap = Map.of("submittedBy", userId);

        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_BY_USER_ID, parameterMap, ROW_MAPPER);
    }

    public void update(String status, int orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("status", status)
                .addValue("updatedAt", new Date())
                .addValue("id", orderId);
       NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_STATUS_BY_ORDER_ID, mapSqlParameterSource);
    }

    public int create(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("status", "unshipped")
                .addValue("submittedBy", user.getId())
                .addValue("submittedAt", new Date())
                .addValue("updatedAt", null);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NAMED_JDBC_TEMPLATE.update(SQL_INSERT, mapSqlParameterSource, keyHolder, new String[] {"id"});
        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
       NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_ORDER_ID, mapSqlParameterSource);
    }
}
