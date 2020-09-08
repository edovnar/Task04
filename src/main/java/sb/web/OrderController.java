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
    public List<Order> getAll(@Param("status") String status) {
        return orderService.getAll();
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
