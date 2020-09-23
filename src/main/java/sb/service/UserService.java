package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.entity.User;
import sb.persistence.dao.UserDAO;
import sb.service.exception.CreationException;
import sb.service.exception.NotFoundException;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public User get(int id) {
        return userDAO.get(id)
                .orElseThrow(() -> new NotFoundException("There is no such user"));
    }

    public User getByName(String name) {
        return userDAO.getByName(name)
                .orElseThrow(() -> new NotFoundException("There is no such user"));
    }

    public User updateRole(int id, String role) {
        userDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such user"));

        if (role != null && (role.equals("admin") || role.equals("user"))) {
            userDAO.updateRole(id, role);
        } else {
            throw new CreationException("The role is designed incorrectly");
        }

        return userDAO.get(id).get();
    }

    public User create(User user) {
        String role = user.getRole();
        int userId;

        if(userDAO.getByName(user.getName()).isPresent()) {
           throw new CreationException("User with this name already exists");
        }

        if(userDAO.getByEmail(user.getEmail()).isPresent()) {
           throw new CreationException("User with this email already exists");
        }

        if (role.equals("admin") || role.equals("user")) {
            userId = userDAO.create(user);
        } else {
            throw new CreationException("Define the role");
        }

        return userDAO.get(userId).get();
    }

    public void delete(int id) {
        userDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userDAO.getByName(name)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No such user")
                );

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}
