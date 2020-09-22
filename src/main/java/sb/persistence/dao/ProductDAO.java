package sb.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDAO {
    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Product> ROW_MAPPER;
    private final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO(NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE) {
        this.NAMED_JDBC_TEMPLATE = NAMED_JDBC_TEMPLATE;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Product.class);
    }

    private final String SQL_SELECT_BY_ID = "select id, supplier_id, name " +
                                            "from products where id = :id";

    private final String SQL_SELECT_ALL = "select id, supplier_id, name " +
                                            "from products";

    private final String SQL_POST = "insert into products(supplier_id, name) " +
                                    "values (:supplierId, :name)";

    private final String SQL_DELETE_BY_PRODUCT_ID = "delete from products where id = :id";

    private final String SQL_UPDATE_BY_PRODUCT_ID = "update products set name = :name where id = :id";


    public Optional<Product> get(int productId) {
        Map<String, Integer> parameterMap = Map.of("id", productId);

        Product product = null;
        try {
            product = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_ID, parameterMap, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e.getMessage());
        }

        return Optional.ofNullable(product);
    }

    public List<Product> getAll() {

        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
    }

    public int post(Product product) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("supplierId", product.getSupplierId())
                .addValue("name", product.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NAMED_JDBC_TEMPLATE.update(SQL_POST, map, keyHolder, new String[]{"id"});
        return keyHolder.getKey().intValue();
    }

    public void delete(int productId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", productId);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_PRODUCT_ID, map);
    }

    public void update(int productId, Product product) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", productId)
                .addValue("name", product.getName());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_PRODUCT_ID, map);
    }
}
