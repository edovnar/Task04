package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import sb.domain.Order;
import sb.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> get(@Param("status") String status){
        return orderService.getByStatus(status);
    }

    @PostMapping
    public void create(@RequestBody Order order){
        orderService.save(order);
    }
}
