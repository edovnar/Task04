package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Supplier;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierDAO {


    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<Supplier> rowMapper;

    public SupplierDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(Supplier.class);
    }

    private final String GET_ALL = "Select * from suppliers ";

    private final String GET = "Select * from suppliers where id = :id";

    private final String GET_BY_NAME = "Select * from suppliers where name = :name";

    private final String UPDATE = "Update suppliers set name = :name, where id = :id";

    private final String POST = "Insert into suppliers values(:id, :userid, :name, :addres, :payernumber," +
            " :registrationcertificatenumber, :reqistrationdate, :phonenumber)";


    public List<Supplier> getAll() {
        return namedJdbcTemplate.query(GET_ALL, rowMapper);
    }

    public Supplier get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(GET, map, rowMapper);
    }

    public Optional<Supplier> getByName(String name) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);

        return Optional.ofNullable(namedJdbcTemplate.queryForObject(GET_BY_NAME, map, rowMapper));
    }

    public void update(Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", supplier.getName())
                .addValue("id", supplier.getId());
        namedJdbcTemplate.update(UPDATE, map);
    }

    public void post(Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", supplier.getId())
                .addValue("name", supplier.getName())
                .addValue("userid", supplier.getUserId())
                .addValue("addres", supplier.getAddress())
                .addValue("id", supplier.getId())
                .addValue("name", supplier.getName());
        namedJdbcTemplate.update(POST, map);
    }
}
