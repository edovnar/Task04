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
import java.util.Map;
import java.util.Optional;

@Repository
public class SupplierDAO {


    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Supplier> ROW_MAPPER;

    public SupplierDAO(NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE) {
        this.NAMED_JDBC_TEMPLATE = NAMED_JDBC_TEMPLATE;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Supplier.class);
    }

    private final static String SQL_SELECT_ALL = "select id, user_id, name, address, payer_number, registration_certificate_number, registration_date, phone_number " +
                                            "from suppliers ";

    private final static String SQL_SELECT_BY_SUPPLIER_ID = "select id, user_id, name, address, payer_number, registration_certificate_number, registration_date, phone_number " +
                                                    "from suppliers where id = :id";

    private final static String SQL_SELECT_BY_USER_ID = "select id, user_id, name, address, payer_number, registration_certificate_number, registration_date, phone_number " +
                                                    "from suppliers where user_id = :userId";

    private final static String SQL_SELECT_BY_SUPPLIER_NAME = "select id, user_id, name, address, payer_number, registration_certificate_number, registration_date, phone_number " +
                                                        "from suppliers where name = :name";

    private final static String SQL_UPDATE_BY_SUPPLIER_ID = "update suppliers set name = :name," +
                                                    "address = :address," +
                                                    "payer_number = :payerNumber, " +
                                                    "registration_certificate_number = :registrationCertificateNumber," +
                                                    "registration_date = :registrationDate," +
                                                    "phone_number = :phoneNumber, " +
                                                    "user_id = :userId where id = :id";

    private final String POST = "insert into suppliers(user_id, name, address, payer_number, registration_certificate_number, registration_date, phone_number) " +
                                "values(:userId, :name, :address, :payerNumber, :registrationCertificateNumber, :registrationDate, :phoneNumber)";

    private final String SQL_DELETE_BY_SUPPLIER_ID = "delete from suppliers where id = :id";

    public List<Supplier> getAll() {
        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
    }

    public Optional<Supplier> get(int supplierId) {
            Map<String, Integer> parameterMap = Map.of("id", supplierId);

            Supplier supplier = null;
            try {
                supplier = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_SUPPLIER_ID, parameterMap, ROW_MAPPER);
            } catch (DataAccessException ignored) {}

        return Optional.ofNullable(supplier);
    }

    public List<Supplier> getByUser(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("userId", id);

        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_BY_USER_ID, map, ROW_MAPPER);
    }

    public Optional<Supplier> getByName(String name) {
        Map<String, String> parameterMap = Map.of("name", name);

        Supplier supplier = null;
        try {
            supplier = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_SUPPLIER_NAME, parameterMap, ROW_MAPPER);
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
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_SUPPLIER_ID, map);
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
        NAMED_JDBC_TEMPLATE.update(POST, map, keyHolder, new String[]{"id"});

        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_SUPPLIER_ID, map);
    }
}
