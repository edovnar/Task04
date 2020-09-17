package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.User;
import sb.persistence.dao.UserDAO;
import sb.utils.mapper.UserMapper;
import sb.domain.dto.UserDTO;
import sb.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return  userMapper.allToModel(userService.getAll());
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable("id") int id) {
        return userMapper.toModel(userService.get(id));
    }

    @PatchMapping("/{id}")
    public UserDTO updateRole(@PathVariable("id") int id,
                                @RequestBody UserDTO userDTO) {

        return userMapper.toModel(userService.updateRole(id, userDTO.getRole()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid User user) {
        return userMapper.toModel(userService.create(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }
}
