package sb.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Stock;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StockDAO {
    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Stock> ROW_MAPPER;

    public StockDAO(NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE) {
        this.NAMED_JDBC_TEMPLATE = NAMED_JDBC_TEMPLATE;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Stock.class);
    }

    private final String SQL_SELECT_ALL = "select id, quantity from stocks ";

    private final String SQL_SELECT_BY_STOCK_ID = "select id, quantity from stocks where id = :id";

    private final String SQL_UPDATE_BY_STOCK_ID = "update stocks set quantity = :quantity where id = :id";

    private final String SQL_POST = "insert into stocks(id, quantity) values(:id, :quantity)";

    private final String SQL_DELETE_BY_STOCK_ID = "delete from stocks where id = :id";

    public List<Stock> getAll() {
        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
    }

    public Optional<Stock> get(int stockId) {
        Map<String, Integer> parameterMap = Map.of("id", stockId);

        Stock stock;
        try {
            stock = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_STOCK_ID, parameterMap, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            Logger log = LoggerFactory.getLogger(StockDAO.class);
            log.info("No stock with the id = " + stockId);
            return Optional.empty();
        }

        return Optional.ofNullable(stock);
    }

    public void update(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_STOCK_ID, map);
    }

    public void post(Stock stock) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        NAMED_JDBC_TEMPLATE.update(SQL_POST, map);
    }

    public void delete(int stockId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", stockId);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_STOCK_ID, map);
    }
}
