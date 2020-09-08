package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.User;
import sb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorExample validatorExample;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {
        return userService.get(id);
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable("id") int id){
    }

    @PostMapping
    public void create(@RequestBody User user, BindingResult bindingResult) {
        validatorExample.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new
        }
        userService.create(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

}
