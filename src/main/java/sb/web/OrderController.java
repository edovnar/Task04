package sb.web;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Order;
import sb.domain.entity.User;
import sb.service.OrderService;
import sb.service.UserService;
import sb.service.exception.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<Order> getAll(@Param("status") String status) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);

        if(user.getRole().equals("admin")){
            return orderService.getAll();
        }
        return orderService.getByUser(user.getId());

    }

    @GetMapping("/{id}")
    public Order get(@PathVariable("id") int id) {
        return orderService.get(id);
    }

    @PostMapping
    public void create(@RequestBody Order order){
        orderService.save(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable("id") int id,
                       @Param("status") String status) {
        orderService.updateStatus(status, id);
    }
}
