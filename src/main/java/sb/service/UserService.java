package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.User;
import sb.persistence.repository.UserRepository;
import sb.service.exception.UserNotFoundException;

@Service
public class UserService extends GeneralService<User> {

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    public void updateStatus(Integer id, String status){
        jpaRepo.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .setStatus(status);
    }
}
