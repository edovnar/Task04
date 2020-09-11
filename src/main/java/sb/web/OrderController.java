package sb.web;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.LineItem;
import sb.domain.entity.Order;
import sb.domain.entity.Product;
import sb.domain.entity.User;
import sb.domain.mapper.LineItemMapper;
import sb.domain.mapper.OrderMapper;
import sb.domain.dto.OrderDTO;
import sb.service.OrderService;
import sb.service.ProductService;
import sb.service.UserService;
import sb.service.exception.CreationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private OrderMapper orderMapper;
    private LineItemMapper lineItemMapper;
    private ProductService productService;

    public OrderController(OrderService orderService,
                           UserService userService,
                           OrderMapper orderMapper,
                           LineItemMapper lineItemMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderMapper = orderMapper;
        this.lineItemMapper = lineItemMapper;
    }

    @GetMapping
    public List<OrderDTO> getAll(@Param("status") String status) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName())
                .orElseThrow(() -> new CreationException("no user"));

        if (user.getRole().equals("admin")) {
            return orderMapper.allToModel(orderService.getAll());
        }
        return orderMapper.allToModel(orderService.getByUser(user.getId()));
    }

    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable("id") int id) {
        return orderMapper.toModel(orderService.get(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Valid List<LineItem> lineItems, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreationException(bindingResult);
        }
        return orderService.save(lineItems);
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
