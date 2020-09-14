package sb.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import sb.domain.dto.ProductDTO;
import sb.domain.dto.UserDTO;
import sb.domain.entity.LineItem;
import sb.domain.entity.Order;
import sb.domain.dto.OrderDTO;
import sb.domain.entity.Product;
import sb.domain.entity.User;
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


    public OrderDTO toModel(Order order) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        List<LineItem> lineItems = lineItemDAO.getByOrder(order.getId());

        ProductDTO productDTO;
        Product product;
        for (LineItem lineItem : lineItems) {
            product = productService.get(lineItem.getProductId());
            productDTO= productMapper.toModel(product);

            productDTO.setQuantity(lineItem.getQuantity());
            productDTOs.add(productDTO);
        }
        UserDTO userDTO = userMapper.toModel(userService.get(order.getSubmittedBy()));
        userDTO.setId(null);
        return new OrderDTO(
                order.getId(),
                order.getShipped(),
                order.getSubmittedAt(),
                userDTO,
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
