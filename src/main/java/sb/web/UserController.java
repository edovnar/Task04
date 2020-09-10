package sb.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.User;
import sb.domain.mapper.UserMapper;
import sb.domain.dto.UserDTO;
import sb.service.UserService;
import sb.service.exception.UserNotFoundException;
import sb.web.Validation.ValidatorExample;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private ValidatorExample validatorExample;
    private UserMapper userMapper;

    public UserController(UserService userService, ValidatorExample validatorExample, UserMapper userMapper) {
        this.userService = userService;
        this.validatorExample = validatorExample;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return  userService.getAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable("id") int id) {
        return userMapper.toModel(userService.get(id));
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable("id") int id){
    }

    @PostMapping
    public void create(@RequestBody @Valid User user, BindingResult bindingResult) {
        validatorExample.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserNotFoundException();
        }
        userService.create(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

}
