package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.User;
import sb.domain.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDTO toModel(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }

    public List<UserDTO> allToModel(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(toModel(user));
        }

        return userDTOs;
    }
}
