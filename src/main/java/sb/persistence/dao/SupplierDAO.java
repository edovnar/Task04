package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Supplier;

import java.util.List;

@Repository
public class SupplierDAO {


    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Supplier> rowMapper;

    public SupplierDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Supplier.class);
    }

    private final String SQL_SELECT_ALL = "Select * from suppliers ";

    private final String SQL_SELECT_BY_ID = "Select * from suppliers where id = :id";

    private final String SQL_SELECT_BY_USERID = "Select * from suppliers where userid = :userId";

    private final String SQL_UPDATE_BY_ID = "Update suppliers set name = :name, where id = :id";

    private final String POST = "Insert into suppliers(userid, name, addres, payernumber, registrationcertificatenumber, reqistrationdate, phonenumber) " +
            "values(:userid, :name, :addres, :payernumber, :registrationcertificatenumber, :reqistrationdate, :phonenumber)";

    private final String SQL_DELETE_BY_ID = "Update products set supplierid=null where exists " +
                                            "(select * from products where supplierid = :id) " +
                                            "and supplierid = :id; " + "Delete from suppliers where id = :id";


    public List<Supplier> getAll() {

        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Supplier get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
    }

    public List<Supplier> getByUser(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("userId", id);

        return namedJdbcTemplate.query(SQL_SELECT_BY_USERID, map, rowMapper);
    }

    public void update(Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", supplier.getName())
                .addValue("id", supplier.getId());
        namedJdbcTemplate.update(SQL_UPDATE_BY_ID, map);
    }

    public void post(Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", supplier.getName())
                .addValue("userid", supplier.getUserId())
                .addValue("addres", supplier.getAddress())
                .addValue("id", supplier.getId())
                .addValue("name", supplier.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(POST, map, keyHolder, new String[]{"id"});
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
