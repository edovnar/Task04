package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import sb.domain.entity.Stock;

import java.util.List;

@Component
public class StockDAO {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Stock> rowMapper;

    public StockDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Stock.class);
    }

    private final String GET_ALL = "Select * from stocks ";

    private final String GET = "Select * from stocks where id = :id";

    private final String UPDATE = "Update stocks set quantity = :quantity where id = :id";

    private final String POST = "Insert into stocks values(:id, :quantity)";

    public List<Stock> getAll() {
        return namedJdbcTemplate.query(GET_ALL, rowMapper);
    }

    public Stock get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(GET, map, rowMapper);
    }

    public void update(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        namedJdbcTemplate.update(UPDATE, map);
    }

    public void post(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        namedJdbcTemplate.update(POST, map);
    }
}
