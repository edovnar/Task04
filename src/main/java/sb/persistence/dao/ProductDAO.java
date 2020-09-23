package sb.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Product;
import sb.utils.PaginationUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductDAO {
    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Product> ROW_MAPPER;
    private final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.NAMED_JDBC_TEMPLATE = namedJdbcTemplate;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Product.class);
    }

    private final static String SQL_SELECT_BY_ID = "select id, supplier_id, name " +
                                            "from products where id = :id";

    private final static String SQL_SELECT_ALL = "select id, supplier_id, name " +
                                            "from products";

    private final static String SQL_POST = "insert into products(supplier_id, name) " +
                                    "values (:supplierId, :name)";

    private final static String SQL_DELETE_BY_PRODUCT_ID = "delete from products where id = :id";

    private final static String SQL_UPDATE_BY_PRODUCT_ID = "update products set name = :name where id = :id";


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

    public List<Product> getAll(Pageable pageable) {

        String query = PaginationUtil.addPaging(SQL_SELECT_ALL, pageable);

        try {

            return NAMED_JDBC_TEMPLATE.query(query, ROW_MAPPER);
        } catch (BadSqlGrammarException e) {
            LOG.info(e.getMessage());

            return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
        }
    }

    public int create(Product product) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("supplierId", product.getSupplierId())
                .addValue("name", product.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NAMED_JDBC_TEMPLATE.update(SQL_POST, parameterMap, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void delete(int productId) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", productId);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_PRODUCT_ID, parameterMap);
    }

    public void update(int productId, Product product) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", productId)
                .addValue("name", product.getName());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_PRODUCT_ID, parameterMap);
    }
}
