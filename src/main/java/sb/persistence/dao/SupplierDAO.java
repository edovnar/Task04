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
import sb.domain.entity.Supplier;

import java.util.*;

@Repository
public class SupplierDAO {

    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<Supplier> ROW_MAPPER;
    private final Logger LOG = LoggerFactory.getLogger(SupplierDAO.class);

    public SupplierDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.NAMED_JDBC_TEMPLATE = namedJdbcTemplate;
        ROW_MAPPER = new BeanPropertyRowMapper<>(Supplier.class);
    }

    private final static String SQL_SELECT_ALL = "select id, user_id, name, address, registration_date, phone_number " +
                                            "from suppliers ";

    private final static String SQL_SELECT_BY_SUPPLIER_ID = "select id, user_id, name, address, registration_date, phone_number " +
                                                    "from suppliers where id = :id";

    private final static String SQL_SELECT_BY_USER_ID = "select id, user_id, name, address, registration_date, phone_number " +
                                                    "from suppliers where user_id = :userId";

    private final static String SQL_SELECT_BY_SUPPLIER_NAME = "select id, user_id, name, address, registration_date, phone_number " +
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
            } catch (EmptyResultDataAccessException e) {
                LOG.info(e.getMessage());
            }

        return Optional.ofNullable(supplier);
    }

    public List<Supplier> getByUserId(int id) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("userId", id);

        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_BY_USER_ID, parameterMap, ROW_MAPPER);
    }

    public void update(int id, Supplier supplier) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", supplier.getName())
                .addValue("userId", supplier.getUserId())
                .addValue("payerNumber", supplier.getPayerNumber())
                .addValue("address", supplier.getAddress())
                .addValue("phoneNumber", supplier.getPhoneNumber())
                .addValue("registrationDate", new Date())
                .addValue("registrationCertificateNumber", supplier.getRegistrationCertificateNumber());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_BY_SUPPLIER_ID, parameterMap);
    }

    public int create(Supplier supplier) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("name", supplier.getName())
                .addValue("userId", supplier.getUserId())
                .addValue("payerNumber", supplier.getPayerNumber())
                .addValue("address", supplier.getAddress())
                .addValue("phoneNumber", supplier.getPhoneNumber())
                .addValue("registrationDate", new Date())
                .addValue("registrationCertificateNumber", supplier.getRegistrationCertificateNumber());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NAMED_JDBC_TEMPLATE.update(POST, parameterMap, keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("id", id);

        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_SUPPLIER_ID, parameterMap);
    }
}
