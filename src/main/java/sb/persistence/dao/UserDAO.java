package sb.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import sb.domain.User;

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

    private final String GET_ALL = "Select * from users ";

    private final String GET = "Select * from users where id = :id";

    private final String GET_BY_NAME = "Select * from users where name = :name";

    private final String UPDATE_STATUS = "Update users set status = :status where id = :id";

    private final String UPDATE = "Update users set name = :name, password = :password, email = :email where id = :id";

    private final String POST = "Insert into users values(:id, :name, :password, :role, :email)";

    private final String DELETE = "Delete from users where id = :id";

    public List<User> getAll() {
        return namedJdbcTemplate.query(GET_ALL, rowMapper);
    }

    public User get(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);

        return namedJdbcTemplate.queryForObject(GET, map, rowMapper);
    }

    public Optional<User> getByName(String name) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", name);

        return Optional.ofNullable(namedJdbcTemplate.queryForObject(GET_BY_NAME, map, rowMapper));
    }

    public void updateStatus(String status, int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("status", status)
                .addValue("id", id);
        namedJdbcTemplate.update(UPDATE_STATUS, map);
    }

    public void update(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("id", user.getId());
        namedJdbcTemplate.update(UPDATE, map);
    }

    public void post(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("password", passwordEncoder.encode(user.getPassword()))
                .addValue("role", user.getRole())
                .addValue("email", user.getEmail());
        namedJdbcTemplate.update(POST, map);
    }

    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(DELETE, map);
    }
}
