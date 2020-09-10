package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Order;
import sb.domain.dto.OrderDTO;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private UserMapper userMapper;
    private LineItemDAO lineItemDAO;
    private LineItemMapper lineItemMapper;

    public OrderMapper(OrderDAO orderDAO, UserDAO userDAO, LineItemDAO lineItemDAO,
                       UserMapper userMapper, LineItemMapper lineItemMapper) {
        this.orderDAO = orderDAO;
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
}
