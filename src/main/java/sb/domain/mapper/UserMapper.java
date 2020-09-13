package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.User;
import sb.domain.dto.UserDTO;
import sb.persistence.dao.UserDAO;

@Component
public class UserMapper {

    public UserDTO toModel(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }
}
