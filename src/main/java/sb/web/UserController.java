package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.User;
import sb.domain.mapper.UserMapper;
import sb.domain.model.UserModel;
import sb.service.UserService;
import sb.service.exception.UserNotFoundException;

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
    public List<UserModel> getAll() {
        return  userService.getAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserModel get(@PathVariable("id") int id) {
        return userMapper.toModel(userService.get(id));
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable("id") int id){
    }

    @PostMapping
    public void create(@RequestBody User user, BindingResult bindingResult) {
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
