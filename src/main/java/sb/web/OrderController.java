package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.dto.LineItemDTO;
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
import sb.utils.OrderSort;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private OrderMapper orderMapper;

    @Autowired
    private OrderSort orderSort;

    public OrderController(OrderService orderService,
                           UserService userService,
                           OrderMapper orderMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderDTO> getAll(@Param("status") String status, @Param("sortBy") String sortBy) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName());

        List<OrderDTO> orders = new ArrayList<>();
        if (user.getRole().equals("admin")) {
           orders = orderMapper.allToModel(orderService.getAll());
           orders = orderSort.sortOrder(sortBy, status, orders);
        }
        return orders;
    }

    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable("id") int id) {

        return orderMapper.toModel(orderService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid List<LineItem> lineItems, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreationException(bindingResult);
        }

        orderService.save(lineItems);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") int id,
                       @Param("status") String status,
                       @RequestBody @Valid LineItem lineItem) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName());
        if(user.getRole().equals("admin")){
            orderService.updateStatus(status, id);
        }
        if(orderService.get(id).getSubmittedBy() == user.getId()){
            orderService.update(id, lineItem);
        }
    }
}
