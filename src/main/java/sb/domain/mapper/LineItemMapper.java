package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.LineItem;
import sb.domain.dto.LineItemDTO;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.ProductDAO;
import sb.service.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineItemMapper {

    private LineItemDAO lineItemDAO;
    private ProductDAO productDAO;
    private ProductMapper productMapper;
    private OrderDAO orderDAO;


    public LineItemMapper(LineItemDAO lineItemDAO,
                          ProductDAO productDAO,
                          ProductMapper productMapper,
                          OrderDAO orderDAO) {
        this.lineItemDAO = lineItemDAO;
        this.productDAO = productDAO;
        this.productMapper = productMapper;
        this.orderDAO = orderDAO;
    }

    public LineItemDTO toModel(LineItem lineItem) {
           return new LineItemDTO(
                   productMapper.toModel(
                           productDAO.get(lineItem.getProductId()).orElseThrow(
                                   () -> new UserNotFoundException("There is no such product"))),
                   lineItem.getQuantity()
           );
    }

    public List<LineItemDTO> allToModel(List<LineItem> lineItems) {
        List<LineItemDTO> o = new ArrayList<>();
        for (LineItem lineItem : lineItems) {
            o.add(toModel(lineItem));
        }
        return o;
    }
}


