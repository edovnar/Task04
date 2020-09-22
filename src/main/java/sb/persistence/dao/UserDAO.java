package sb.persistence.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import sb.domain.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAO {

    private final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;
    private final BeanPropertyRowMapper<User> ROW_MAPPER;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDAO(NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE) {
        this.NAMED_JDBC_TEMPLATE = NAMED_JDBC_TEMPLATE;
        ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    }

    private final static String SQL_SELECT_ALL = "select id, name, password, role, email " +
                                            "from users";

    private final static String SQL_SELECT_BY_ID = "select id, name, password, role, email " +
                                            "from users where id = :id";

    private final static String SQL_SELECT_BY_NAME = "select id, name, password, role, email " +
                                                "from users where name = :name";

    private final static String SQL_SELECT_BY_EMAIL = "select id, name, password, role, email " +
                                                "from users where email = :email";

    private final static String SQL_UPDATE_ROLE_BY_ID = "update users set role = :role where id = :id";

    private final static String SQL_UPDATE_ID = "update users set name = :name, password = :password, email = :email where id = :id";

    private final static String SQL_POST = "insert into users(name, password, role, email) values(:name, :password, :role, :email)";

    private final static String SQL_DELETE_BY_ID = "delete from users where id = :id";

    public List<User> getAll() {
        return NAMED_JDBC_TEMPLATE.query(SQL_SELECT_ALL, ROW_MAPPER);
    }

    public Optional<User> get(int id) {
        Map parameterMap = Map.of("id", id);

        User user = null;
        try {
            user = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_ID, parameterMap, ROW_MAPPER);
        } catch (EmptyResultDataAccessException ignored) {}

        return Optional.ofNullable(user);
    }

    public Optional<User> getByName(String name) {
        Map<String, String> parameterMap = Map.of("name", name);

        User user = null;
        try {
            user = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_NAME, parameterMap, ROW_MAPPER);
        } catch (DataAccessException ignored) { }

        return Optional.ofNullable(user);
    }

    public Optional<User> getByEmail(String email) {
        Map<String, String> parameterMap = Map.of("email", email);

        User user = null;
        try {
            user = NAMED_JDBC_TEMPLATE.queryForObject(SQL_SELECT_BY_EMAIL, parameterMap, ROW_MAPPER);
        } catch (DataAccessException ignored){}

        return Optional.ofNullable(user);
    }

    public void updateRole(int id, String role) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("role", role)
                .addValue("id", id);
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_ROLE_BY_ID, map);
    }

    public void update(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("id", user.getId());
        NAMED_JDBC_TEMPLATE.update(SQL_UPDATE_ID, map);
    }

    public int post(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", passwordEncoder.encode(user.getPassword()))
                .addValue("role", user.getRole())
                .addValue("email", user.getEmail());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NAMED_JDBC_TEMPLATE.update(SQL_POST, map, keyHolder, new String[]{"id"});

        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        NAMED_JDBC_TEMPLATE.update(SQL_DELETE_BY_ID, map);
    }
}
