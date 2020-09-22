package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.dto.request.UserDTORequest;
import sb.domain.entity.User;
import sb.domain.dto.response.UserDTOResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDTOResponse toModel(User user) {
        return new UserDTOResponse(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }

    public List<UserDTOResponse> allToModel(List<User> users) {
        List<UserDTOResponse> userDTOResponses = new ArrayList<>();
        for (User user : users) {
            userDTOResponses.add(toModel(user));
        }

        return userDTOResponses;
    }

    public User toEntity(Integer id, UserDTORequest userDTORequest) {
        return new User(
                id,
                userDTORequest.getName(),
                userDTORequest.getPassword(),
                userDTORequest.getRole(),
                userDTORequest.getEmail()
        );
    }
}
