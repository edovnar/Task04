package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Order;
import sb.domain.model.OrderModel;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.UserDAO;

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

    public OrderModel toModel(Order order) {
        return new OrderModel(
                userMapper.toModel(
                        userDAO.get(order.getSubmittedBy())
                ),
                lineItemMapper.toModel(
                        lineItemDAO.getByOrder(order.getId())
                )
        );
    }
}
