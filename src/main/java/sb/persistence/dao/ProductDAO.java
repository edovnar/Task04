package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Product;

import java.util.List;

@Repository
public class ProductDAO {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Product> rowMapper;

    public ProductDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Product.class);
    }

    private final String GET = "Select * from products where id = :id";

    private final String GET_ALL = "Select * from products";

    private final String GET_BY_NAME = "Select * from products where name = :name";

    private final String GET_BY_SUPPLIER = "Select * from products where supplierid = :supplierId";

    private final String GET_BY_STOCK = "Select * from products where stockid = :stockId";

    private final String POST = "Insert into products values (:id, :suppliedId, :stockId, :name)";

    private final String DELETE = "Delete from products where id = :id";

    private final String UPDATE = "Update products set name = :name where id = :id";


    public Product get(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(GET, map, rowMapper);
    }


    public Product getByName(String name){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);

        return namedJdbcTemplate.queryForObject(GET_BY_NAME, map, rowMapper);
    }


    public Product getByStock(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("stockId", id);

        return namedJdbcTemplate.queryForObject(GET_BY_STOCK, map, rowMapper);
    }


    public List<Product> getAll(){

        return namedJdbcTemplate.query(GET_ALL, rowMapper);
    }


    public List<Product> getBySupplier(int id){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.query(GET_BY_SUPPLIER, map, rowMapper);
    }


    public void post(Product product) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("suppliedId", product.getSupplierId())
                .addValue("stockId", product.getStockId())
                .addValue("name", product.getName());
        namedJdbcTemplate.update(POST, map);
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(DELETE, map);
    }

    public void update(Product product){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("name", product.getName());
        namedJdbcTemplate.update(UPDATE, map);
    }
}
