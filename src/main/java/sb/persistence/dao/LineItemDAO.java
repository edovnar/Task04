package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private final String GET =
            "Select * from lineitems where orderid = :orderId " +
                    "and productid = :productId";

    private final String GET_ALL = "Select * from lineitems";

    private final String GET_BY_ORDER = "Select * from lineitems where orderid = :orderId";

    public List<LineItem> get(int orderId, int productId){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("productId", productId);

        return namedJdbcTemplate.query(GET, map, rowMapper);
    }

    public List<LineItem> getAll(){
        return namedJdbcTemplate.query(GET, rowMapper);
    }


    public List<LineItem> getByOrder(int orderId){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("orderId", orderId);

        return namedJdbcTemplate.query(GET_BY_ORDER, rowMapper);
    }
}
