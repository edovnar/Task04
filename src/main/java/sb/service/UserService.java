package sb.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sb.domain.entity.User;
import sb.persistence.dao.UserDAO;
import sb.service.exception.CreationException;

import java.util.List;
import java.util.Optional;

@Component
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public User get(int id) {
        return userDAO.get(id);
    }

    public Optional<User> getByName(String name) {
        return userDAO.getByName(name);
    }

    public void updateStatus(int id, String role) {
        if (role.equals("admin") || role.equals("user")) {
        userDAO.updateStatus(id, role);
        } else {
            throw new CreationException("The role is designed incorrectly");
        }
    }

    public void create(User user) {
        String role = user.getRole();
        if (userDAO.getByEmail(user.getEmail()).isEmpty() && user.getEmail()!=null) {
            if (role.equals("admin") || role.equals("user")) {

                userDAO.post(user);

            } else throw new CreationException("Define the role");
        } else throw new CreationException("Invalid email (User with this email is already exists or is empty)");
    }

    public void delete(int id) {
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
