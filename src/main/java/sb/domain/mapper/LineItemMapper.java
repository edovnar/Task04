package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.dto.OrderDTO;
import sb.domain.entity.LineItem;
import sb.domain.dto.LineItemDTO;
import sb.domain.entity.Order;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.ProductDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class LineItemMapper {

    private LineItemDAO lineItemDAO;
    private ProductDAO productDAO;
    private ProductMapper productMapper;


    public LineItemMapper(LineItemDAO lineItemDAO, ProductDAO productDAO, ProductMapper productMapper) {
        this.lineItemDAO = lineItemDAO;
        this.productDAO = productDAO;
        this.productMapper = productMapper;
    }

    public LineItemDTO toModel(LineItem lineItem) {
           return new LineItemDTO(
                   productMapper.toModel(
                           productDAO.get(lineItem.getProductId())),
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


