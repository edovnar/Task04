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
    private final Logger LOG = LoggerFactory.getLogger(StockDAO.class);

    public StockDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.NAMED_JDBC_TEMPLATE = namedJdbcTemplate;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Stock.class);
    }

    private final static String SQL_SELECT_ALL = "select id, quantity from stocks ";

    private final static String SQL_SELECT_BY_STOCK_ID = "select id, quantity from stocks where id = :id";

    private final static String SQL_UPDATE_BY_STOCK_ID = "update stocks set quantity = :quantity where id = :id";

    private final static String SQL_POST = "insert into stocks(id, quantity) values(:id, :quantity)";

    private final static String SQL_DELETE_BY_STOCK_ID = "delete from stocks where id = :id";

    public List<Stock> getAll() {
        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
    }

    public Optional<Stock> get(int stockId) {
        Map<String, Integer> parameterMap = Map.of("id", stockId);

        Stock stock = null;
        try {
            stock = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_STOCK_ID, parameterMap, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e.getMessage());
        }

        return Optional.ofNullable(stock);
    }

    public void update(Stock stock) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_STOCK_ID, parameterMap);
    }

    public void create(Stock stock) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("quantity", stock.getQuantity())
                .addValue("id", stock.getId());
        NAMED_JDBC_TEMPLATE.update(SQL_POST, parameterMap);
    }

    public void delete(int stockId) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", stockId);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_STOCK_ID, parameterMap);
    }
}
