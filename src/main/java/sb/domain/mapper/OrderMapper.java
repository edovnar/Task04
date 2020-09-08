package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Order;
import sb.domain.model.OrderModel;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.UserDAO;

@Component
public class OrderMapper {

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final LineItemDAO lineItemDAO;

    public OrderMapper(OrderDAO orderDAO, UserDAO userDAO, LineItemDAO lineItemDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.lineItemDAO = lineItemDAO;
    }

    public OrderModel toModel(Order order) {
        return new OrderModel(
                order.getId(),
                userDAO.get(order.getSubmittedBy()),
                lineItemDAO.getByOrder(order.getId())
                );
    }
}
