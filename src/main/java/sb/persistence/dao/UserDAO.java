package sb.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sb.domain.User;

import java.util.List;

@Component
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public List<User> getAll() {
        return  jdbc.query(
                "select * from users",
                new BeanPropertyRowMapper<>(User.class)
        );
    }

    public User get(int id) {
        return jdbc.query(
                "select * from users where id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(User.class)
        ).stream().findAny().orElse(null);
    }
}
