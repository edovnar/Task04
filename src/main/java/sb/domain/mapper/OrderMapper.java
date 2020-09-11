package sb.domain.mapper;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sb.domain.entity.Order;
import sb.domain.dto.OrderDTO;
import sb.domain.entity.User;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.UserDAO;
import sb.service.UserService;
import sb.service.exception.CreationException;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private UserService userService;
    private UserDAO userDAO;
    private UserMapper userMapper;
    private LineItemDAO lineItemDAO;
    private LineItemMapper lineItemMapper;

    public OrderMapper(UserService userService, UserDAO userDAO, LineItemDAO lineItemDAO,
                       UserMapper userMapper, LineItemMapper lineItemMapper) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.lineItemDAO = lineItemDAO;
        this.userMapper = userMapper;
        this.lineItemMapper = lineItemMapper;
    }

    public OrderDTO toModel(Order order) {
        return new OrderDTO(
                order.getId(),
                userMapper.toModel(
                        userDAO.get(order.getSubmittedBy())
                ),
                lineItemMapper.allToModel(
                        lineItemDAO.getByOrder(order.getId())
                )
        );
    }

    public List<OrderDTO> allToModel(List<Order> orders) {
        List<OrderDTO> o = new ArrayList<>();
        for (Order order : orders) {
            o.add(toModel(order));
        }
        return o;
    }

//    public Order toEntity(OrderDTO orderDTO) {
//        SecurityContext context = SecurityContextHolder.getContext();
//        User user = userService.getByName(context.getAuthentication().getName())
//                .orElseThrow(()->new CreationException("no user"));
//        return new Order(
//                orderDTO.getId(),
//                userMapper.toModel(user),
//                lineItemDAO.getByOrder(orderDTO.getId())
//        );
//    }
}
