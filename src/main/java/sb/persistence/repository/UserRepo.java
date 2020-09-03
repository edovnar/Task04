package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.User;

@Component
interface UserRepo extends JpaRepository<User, Integer> {
}
