package sb.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.dto.LineItemDTO;
import sb.domain.dto.UserDTO;
import sb.domain.entity.Order;
import sb.domain.dto.response.OrderDTOResponse;
import sb.persistence.dao.LineItemDAO;
import sb.service.ProductService;
import sb.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LineItemDAO lineItemDAO;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private LineItemMapper lineItemMapper;


    public OrderDTOResponse toModel(Order order) {

        List<LineItemDTO> lineItemDTOs = lineItemMapper.allToModel(
                lineItemDAO.getByOrder(order.getId())
        );

        UserDTO userDTO = userMapper.toModel(userService.get(order.getSubmittedBy()));
        userDTO.setId(null);
        return new OrderDTOResponse(
                order.getId(),
                order.getStatus(),
                order.getSubmittedAt(),
                userDTO,
                lineItemDTOs
        );
    }

    public List<OrderDTOResponse> allToModel(List<Order> orders) {
        List<OrderDTOResponse> orderDTOResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDTOResponses.add(toModel(order));
        }
        return orderDTOResponses;
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
