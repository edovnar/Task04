package sb.web;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Order;
import sb.domain.entity.User;
import sb.domain.mapper.OrderMapper;
import sb.domain.model.OrderModel;
import sb.service.OrderService;
import sb.service.UserService;
import sb.service.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, UserService userService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderModel> getAll(@Param("status") String status) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);

        if(user.getRole().equals("admin")){
            return orderService.getAll().stream()
                    .map((orderMapper::toModel))
                    .collect(Collectors.toList());
        }
        return orderService.getByUser(user.getId()).stream()
                .map((orderMapper::toModel))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderModel get(@PathVariable("id") int id) {
        return orderMapper.toModel(orderService.get(id));
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
