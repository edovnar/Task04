package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sb.domain.entity.LineItem;

import java.util.List;
import java.util.Map;

@Repository
public class LineItemDAO {

    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<LineItem> ROW_MAPPER;

    public LineItemDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.NAMED_JDBC_TEMPLATE = namedJdbcTemplate;
        ROW_MAPPER = new BeanPropertyRowMapper<>(LineItem.class);
    }

    private final static String SQL_SELECT_BY_ORDER_ID_PRODUCT_ID = "select order_id, product_id, quantity " +
                                                                    "from lineitems " +
                                                                    "where order_id = :orderId and product_id = :productId";

    private final static String SQL_SELECT_BY_ORDER_ID = "select order_id, product_id, quantity " +
                                                        "from lineitems where order_id = :orderId";

    private final static String SQL_UPDATE_BY_ORDER_ID_PRODUCT_ID = "update lineitems set quantity = :quantity " +
                                                                    "where order_id = :orderId and product_id = :productId";

    private final static String SQL_INSERT = "insert into lineitems(order_id, product_id, quantity) " +
                                                "values(:orderId, :productId, :quantity)";

    private final static String SQL_DELETE_BY_ORDER_ID = "delete from lineitems where order_id = :id";

    public LineItem get(int orderId, int productId){
        Map<String, Integer> parameterMap = Map.of("orderId", orderId, "productId", productId);

        return NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_ORDER_ID_PRODUCT_ID, parameterMap, ROW_MAPPER);
    }

    public List<LineItem> getByOrderId(int orderId) {
        Map<String, Integer> parameterMap = Map.of("orderId", orderId);

        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_BY_ORDER_ID, parameterMap, ROW_MAPPER);
    }

    public void create(LineItem lineItem) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("orderId", lineItem.getOrderId())
                .addValue("productId", lineItem.getProductId())
                .addValue("quantity", lineItem.getQuantity());
        NAMED_JDBC_TEMPLATE.update(SQL_INSERT, parameterMap);
    }

    public void update(LineItem lineItem) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("quantity", lineItem.getQuantity())
                .addValue("orderId", lineItem.getOrderId())
                .addValue("productId", lineItem.getProductId());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_ORDER_ID_PRODUCT_ID, parameterMap);
    }

    public void deleteByOrder(int orderId) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", orderId);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_ORDER_ID, parameterMap);
    }
}
