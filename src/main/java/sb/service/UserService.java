package sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.User;

@Component
public class UserService extends Service<User> {

    public UserService(JpaRepository<User, Integer> jpaRepo) {
        super(jpaRepo);
    }
}
