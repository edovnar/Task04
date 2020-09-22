package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.dto.response.LineItemDTOResponse;
import sb.domain.dto.response.UserDTOResponse;
import sb.domain.entity.Order;
import sb.domain.dto.response.OrderDTOResponse;
import sb.persistence.dao.LineItemDAO;
import sb.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LineItemDAO lineItemDAO;
    private final LineItemMapper lineItemMapper;

    public OrderMapper(UserService userService, UserMapper userMapper, LineItemDAO lineItemDAO, LineItemMapper lineItemMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.lineItemDAO = lineItemDAO;
        this.lineItemMapper = lineItemMapper;
    }

    public OrderDTOResponse toModel(Order order) {

        List<LineItemDTOResponse> lineItemDTOResponses = lineItemMapper.allToModel(
                lineItemDAO.getByOrder(order.getId())
        );

        UserDTOResponse userDTOResponse = userMapper.toModel(userService.get(order.getSubmittedBy()));
        userDTOResponse.setId(null);

        return new OrderDTOResponse(
                order.getId(),
                order.getStatus(),
                order.getSubmittedAt(),
                userDTOResponse,
                lineItemDTOResponses
        );
    }

    public List<OrderDTOResponse> allToModel(List<Order> orders) {
        List<OrderDTOResponse> orderDTOResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDTOResponses.add(toModel(order));
        }

        return orderDTOResponses;
    }
}
