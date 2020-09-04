package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.domain.User;
import sb.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"id"})
    public User getUser(@PathVariable int id){
        return userService.getById(id);
    }

    @PatchMapping(value = {"id"})
    public void changeStatus(@PathVariable int id){
        userService.getById(id).setStatus("admin");
    }




}
