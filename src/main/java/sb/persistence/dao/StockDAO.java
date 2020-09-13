package sb.persistence.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import sb.domain.entity.Stock;
import sb.service.exception.StockNotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class StockDAO {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Stock> rowMapper;

    public StockDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Stock.class);
    }

    private final String SQL_SELECT_ALL = "select * from stocks ";

    private final String SQL_SELECT_BY_ID = "select * from stocks where id = :id";

    private final String SQL_UPDATE_BY_ID = "update stocks set quantity = :quantity where id = :id";

    private final String POST = "insert into stocks(id, quantity) values(:id, :quantity)";

    public List<Stock> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Optional<Stock> get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        Stock stock = null;
        try {
            stock = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
        } catch (EmptyResultDataAccessException ignored) {}

        return Optional.ofNullable(stock);
    }

    public void update(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        namedJdbcTemplate.update(SQL_UPDATE_BY_ID, map);
    }

    public void post(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        namedJdbcTemplate.update(POST, map);
    }
}
