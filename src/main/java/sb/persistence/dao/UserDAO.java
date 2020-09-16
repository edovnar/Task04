package sb.persistence.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import sb.domain.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private BeanPropertyRowMapper<User> rowMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDAO(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        rowMapper = new BeanPropertyRowMapper<>(User.class);
    }

    private final String SQL_SELECT_ALL = "select * from users ";

    private final String SQL_SELECT_BY_ID = "select * from users where id = :id";

    private final String SQL_SELECT_BY_NAME = "select * from users where name = :name";

    private final String SQL_SELECT_BY_EMAIL = "select * from users where email = :email";

    private final String SQL_UPDATE_ROLE_BY_ID = "update users set role = :role where id = :id";

    private final String SQL_UPDATE_ID = "update users set name = :name, password = :password, email = :email where id = :id";

    private final String SQL_POST = "insert into users(name, password, role, email) values(:name, :password, :role, :email)";

    private final String SQL_DELETE_BY_ID = "delete from users where id = :id";

    public List<User> getAll() {
        return namedJdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public Optional<User> get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        User user = null;
        try {
            user = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_ID, map, rowMapper);
        } catch (EmptyResultDataAccessException ignored) {}

        return Optional.ofNullable(user);
    }

    public Optional<User> getByName(String name) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);
        User user = null;
        try {
            user = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, map, rowMapper);
        } catch (DataAccessException ignored) { }

        return Optional.ofNullable(user);
    }

    public Optional<User> getByEmail(String email) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("email", email);
        User user = null;
        try {
            user = namedJdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, map, rowMapper);
        } catch (DataAccessException ignored){}

        return Optional.ofNullable(user);
    }

    public void updateRole(int id, String role) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("role", role)
                .addValue("id", id);
        namedJdbcTemplate.update(SQL_UPDATE_ROLE_BY_ID, map);
    }

    public void update(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("id", user.getId());
        namedJdbcTemplate.update(SQL_UPDATE_ID, map);
    }

    public int post(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", passwordEncoder.encode(user.getPassword()))
                .addValue("role", user.getRole())
                .addValue("email", user.getEmail());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(SQL_POST, map, keyHolder, new String[]{"id"});

        return keyHolder.getKey().intValue();
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(SQL_DELETE_BY_ID, map);
    }
}
