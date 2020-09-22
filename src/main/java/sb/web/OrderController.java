package sb.web;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.dto.request.OrderDTORequest;
import sb.domain.entity.Order;
import sb.domain.entity.User;
import sb.utils.mapper.OrderMapper;
import sb.domain.dto.response.OrderDTOResponse;
import sb.service.OrderService;
import sb.service.UserService;
import sb.utils.PaginationUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private OrderMapper orderMapper;
    private PaginationUtil paginationUtil;

    public OrderController(OrderService orderService, UserService userService, OrderMapper orderMapper, PaginationUtil paginationUtil) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderMapper = orderMapper;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public List<OrderDTOResponse> getAll(Pageable pageable) {
        List<OrderDTOResponse> orderDTOs = orderMapper.allToModel(orderService.getAll(pageable));

        return orderDTOs;
    }

    @GetMapping("/{id}")
    public OrderDTOResponse get(@PathVariable("id") int id) {
        return orderMapper.toModel(orderService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTOResponse create(@RequestBody @Valid OrderDTORequest orderDTORequest) {

        Order order = orderService.create(orderDTORequest);

        return orderMapper.toModel(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }

    @PutMapping("/{id}")
    public OrderDTOResponse update(@PathVariable("id") int id,
                                   @RequestBody @Valid OrderDTORequest orderDTORequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.getByName(context.getAuthentication().getName());

        if(orderService.get(id).getSubmittedBy().equals(user.getId())){
            return orderMapper.toModel(orderService.update(id, orderDTORequest));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{id}")
    public OrderDTOResponse updateStatus (@PathVariable("id") int id,
                                          @RequestBody OrderDTORequest orderDTORequest) {
        return orderMapper.toModel(orderService.updateStatus(orderDTORequest.getStatus(), id));
    }
}
