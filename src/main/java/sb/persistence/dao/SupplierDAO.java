package sb.persistence.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.Supplier;

import java.util.Date;
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

    private final String SQL_SELECT_ALL = "select * from suppliers ";

    private final String SQL_SELECT_BY_ID = "select * from suppliers where id = :id";

    private final String SQL_SELECT_BY_USERID = "select * from suppliers where userid = :userId";

    private final String SQL_SELECT_BY_NAME = "select * from suppliers where name = :name";

    private final String SQL_UPDATE_BY_ID = "update suppliers set name = :name," +
                                            "address = :address," +
                                            "payernumber = :payerNumber, " +
                                            "registrationcertificatenumber = :registrationCertificateNumber," +
                                            "registrationdate = :registrationDate," +
                                            "phonenumber = :phoneNumber, " +
                                            "userid = :userId where id = :id";

    private final String POST = "insert into suppliers(userid, name, address, payernumber, registrationcertificatenumber, registrationdate, phonenumber) " +
            "values(:userId, :name, :address, :payerNumber, :registrationCertificateNumber, :registrationDate, :phoneNumber)";

    private final String SQL_DELETE_BY_ID = "delete from suppliers where id = :id";

    public List<Supplier> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Optional<Supplier> get(int id) {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", id);
            Supplier supplier = null;
            try {
                supplier = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
            } catch (DataAccessException ignored) {}

        return Optional.ofNullable(supplier);
    }

    public List<Supplier> getByUser(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("userId", id);

        return namedJdbcTemplate.query(SQL_SELECT_BY_USERID, map, rowMapper);
    }

    public Optional<Supplier> getByName(String name) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);
        Supplier supplier = null;
        try {
            supplier = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, map, rowMapper);
        } catch (DataAccessException ignored) {}

        return Optional.ofNullable(supplier);
    }

    public void update(int id, Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", supplier.getName())
                .addValue("userId", supplier.getUserId())
                .addValue("payerNumber", supplier.getPayerNumber())
                .addValue("address", supplier.getAddress())
                .addValue("phoneNumber", supplier.getPhoneNumber())
                .addValue("registrationDate", new Date())
                .addValue("registrationCertificateNumber", supplier.getRegistrationCertificateNumber());
        namedJdbcTemplate.update(SQL_UPDATE_BY_ID, map);
    }

    public int post(Supplier supplier) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", supplier.getName())
                .addValue("userId", supplier.getUserId())
                .addValue("payerNumber", supplier.getPayerNumber())
                .addValue("address", supplier.getAddress())
                .addValue("phoneNumber", supplier.getPhoneNumber())
                .addValue("registrationDate", new Date())
                .addValue("registrationCertificateNumber", supplier.getRegistrationCertificateNumber());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(POST, map, keyHolder, new String[]{"id"});

        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
