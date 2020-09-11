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

    private final String SQL_SELECT_BY_ORDERID_PRODUCTID =
            "Select * from lineitems where orderid = :orderId " +
                    "and productid = :productId";

    private final String SQL_SELECT_ALL = "select * from lineitems";

    private final String SQL_SELECT_BY_ORDER = "select * from lineitems where orderid = :orderId";

    private final String SQL_INSERT = "insert into lineitems(orderid, productid, quantity) values(:orderid, :productid, quantity)";


    public List<LineItem> get(int orderId, int productId){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("productId", productId);

        return namedJdbcTemplate.query(SQL_SELECT_BY_ORDERID_PRODUCTID, map, rowMapper);
    }

    public List<LineItem> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }


    public List<LineItem> getByOrder(int orderId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId);

        return namedJdbcTemplate.query(SQL_SELECT_BY_ORDER, map, rowMapper);
    }

//    public List<LineItem> getByProduct

    public void post(LineItem lineItem) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderid", lineItem.getOrderId())
                .addValue("productid", lineItem.getProductId())
                .addValue("quantity", lineItem.getQuantity());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(SQL_INSERT, map, keyHolder, new String[]{"id"});
    }
}
