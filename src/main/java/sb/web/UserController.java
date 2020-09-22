package sb.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sb.domain.dto.request.UserDTORequest;
import sb.domain.entity.User;
import sb.utils.mapper.UserMapper;
import sb.domain.dto.response.UserDTOResponse;
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
    public List<UserDTOResponse> getAll() {
        return  userMapper.allToModel(userService.getAll());
    }

    @GetMapping("/{id}")
    public UserDTOResponse get(@PathVariable("id") int id) {
        return userMapper.toModel(userService.get(id));
    }

    @PatchMapping("/{id}")
    public UserDTOResponse updateRole(@PathVariable("id") int id,
                                      @RequestBody UserDTORequest userDTORequest) {

        return userMapper.toModel(userService.updateRole(id, userDTORequest.getRole()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOResponse create(@RequestBody @Valid UserDTORequest userDTORequest) {
        User user = userMapper.toEntity(null, userDTORequest);
        return userMapper.toModel(userService.create(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }
}
