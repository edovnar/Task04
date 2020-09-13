package sb.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sb.domain.dto.ProductDTO;
import sb.domain.entity.LineItem;
import sb.domain.entity.Order;
import sb.domain.dto.OrderDTO;
import sb.domain.entity.Product;
import sb.domain.entity.User;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.UserDAO;
import sb.service.ProductService;
import sb.service.UserService;
import sb.service.exception.CreationException;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public OrderDTO toModel(Order order) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        List<LineItem> lineItems = lineItemDAO.getByOrder(order.getId());

        for (LineItem lineItem : lineItems) {
            Product product = productService.get(lineItem.getProductId());
            productDTOs.add(
                    productMapper.toModel(product)
            );
        }
        return new OrderDTO(
                order.getId(),
                order.getShipped(),
                order.getSubmittedAt(),
                userMapper.toModel(
                        userService.get(order.getSubmittedBy())
                ),
                productDTOs
        );
    }

    public List<OrderDTO> allToModel(List<Order> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(toModel(order));
        }
        return orderDTOs;
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
