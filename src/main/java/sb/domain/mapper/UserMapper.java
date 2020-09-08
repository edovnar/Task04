package sb.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.User;
import sb.domain.model.UserModel;
import sb.persistence.dao.UserDAO;

@Component
public class UserMapper {

    private final UserDAO userDAO;

    public UserMapper(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserModel toModel(User user){
        return new UserModel(user.getName(), user.getRole());
    }
}
