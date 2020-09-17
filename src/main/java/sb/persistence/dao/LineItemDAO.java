package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.LineItem;

import java.util.List;

@Repository
public class LineItemDAO {

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<LineItem> rowMapper;

    public LineItemDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(LineItem.class);
    }

    private final static String SQL_SELECT_BY_ORDERID_PRODUCTID = "select order_id, product_id, quantity " +
                                                                    "from lineitems " +
                                                                    "where order_id = :orderId and product_id = :productId";

    private final static String SQL_SELECT_ALL = "select order_id, product_id, quantity " +
                                                    "from lineitems";

    private final static String SQL_SELECT_BY_ORDER = "select order_id, product_id, quantity " +
                                                        "from lineitems where order_id = :orderId";

    private final static String SQL_SELECT_BY_PRODUCT = "select order_id, product_id, quantity " +
                                                        "from lineitems where product_id = :productId";

    private final static String SLQ_UPDATE = "update lineitems set quantity = :quantity " +
                                                "where order_id = :orderId and product_id = :productId";

    private final static String SQL_INSERT = "insert into lineitems(order_id, product_id, quantity) " +
                                                "values(:orderId, :productId, :quantity)";

    private final static String SQL_DELETE_BY_ID = "delete from lineitems where order_id = :id";

    public LineItem get(int orderId, int productId){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("productId", productId);

        return namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ORDERID_PRODUCTID, map, rowMapper);
    }

    public List<LineItem> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }


    public List<LineItem> getByOrder(int orderId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId);

        return namedJdbcTemplate.query(SQL_SELECT_BY_ORDER, map, rowMapper);
    }

    public List<LineItem> getByProduct(int productId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("productId", productId);

        return namedJdbcTemplate.query(SQL_SELECT_BY_PRODUCT, map, rowMapper);
    }

    public void post(LineItem lineItem) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", lineItem.getOrderId())
                .addValue("productId", lineItem.getProductId())
                .addValue("quantity", lineItem.getQuantity());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(SQL_INSERT, map, keyHolder, new String[]{"id"});
    }

    public void update(LineItem lineItem) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", lineItem.getQuantity())
                .addValue("orderId", lineItem.getOrderId())
                .addValue("productId", lineItem.getProductId());

        namedJdbcTemplate.update(SLQ_UPDATE,map);
    }

    public void deleteByOrder(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
