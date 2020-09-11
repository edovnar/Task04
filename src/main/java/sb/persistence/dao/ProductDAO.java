package sb.persistence.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAO {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Product> rowMapper;

    public ProductDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Product.class);
    }

    private final String SQL_SELECT_BY_ID = "Select * from products where id = :id";

    private final String SQL_SELECT_ALL = "Select * from products";

    private final String SQL_SELECT_BY_NAME = "Select * from products where name = :name";

    private final String SQL_SELECT_BY_SUPPLIERID = "Select * from products where supplierid = :supplierId";

    private final String SQL_SELECT_BY_STOCKID = "Select * from products where stockid = :stockId";

    private final String SQL_POST = "Insert into products(suppliedId, name) values (:suppliedId, :name)";

    private final String SQL_DELETE_BY_ID = "Delete from products where id = :id";

    private final String SQL_UPDATE_BY_ID = "Update products set name = :name where id = :id";


    public Optional<Product> get(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        Product product = null;
        try {
            product = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
        } catch (DataAccessException ignored){}

        return Optional.ofNullable(product);
    }


    public Product getByName(String name){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);

        return namedJdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, map, rowMapper);
    }


    public Product getByStock(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("stockId", id);

        return namedJdbcTemplate.queryForObject(SQL_SELECT_BY_STOCKID, map, rowMapper);
    }


    public List<Product> getAll(){

        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }


    public List<Product> getBySupplier(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.query(SQL_SELECT_BY_SUPPLIERID, map, rowMapper);
    }


    public void post(Product product) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("suppliedId", product.getSupplierId())
                .addValue("name", product.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(SQL_POST, map, keyHolder, new String[]{"id"});
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }

    public void update(Product product){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("name", product.getName());
        namedJdbcTemplate.update(SQL_UPDATE_BY_ID, map);
    }
}
