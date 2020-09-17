package sb.persistence.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public class StockDAO {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Stock> rowMapper;

    public StockDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Stock.class);
    }

    private final String SQL_SELECT_ALL = "select id, quantity from stocks ";

    private final String SQL_SELECT_BY_ID = "select id, quantity from stocks where id = :id";

    private final String SQL_UPDATE_BY_ID = "update stocks set quantity = :quantity where id = :id";

    private final String SQL_POST = "insert into stocks(id, quantity) values(:id, :quantity)";

    private final String SQL_DELETE_BY_ID = "delete from stocks where id = :id";

    public List<Stock> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Optional<Stock> get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        Stock stock = null;
        try {
            stock = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
        } catch (DataAccessException ignored) {}

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
        namedJdbcTemplate.update(SQL_POST, map);
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
