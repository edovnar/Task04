package sb.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.LineItem;
import sb.domain.dto.LineItemDTO;
import sb.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineItemMapper {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;


    public LineItemDTO toModel(LineItem lineItem) {
           return new LineItemDTO(
                   lineItem.getOrderId(),
                   productMapper.toModel(
                           productService.get(lineItem.getProductId())
                   ),
                   lineItem.getQuantity()
           );
    }

    public List<LineItemDTO> allToModel(List<LineItem> lineItems) {
        List<LineItemDTO> lineItemDTOs = new ArrayList<>();
        for (LineItem lineItem : lineItems) {
            lineItemDTOs.add(toModel(lineItem));
        }
        return lineItemDTOs;
    }

    public LineItem toEntity(LineItemDTO lineItemDTO) {
        int productId = lineItemDTO.getProduct().getId();
        int orderId = lineItemDTO.getOrderId();

        return new LineItem(orderId, productId, lineItemDTO.getQuantity());
    }

    public List<LineItem> allToEntity(List<LineItemDTO> lineItemDTOs) {
        List<LineItem> lineItems = new ArrayList<>();
        for (LineItemDTO lineItemDTO : lineItemDTOs) {
            lineItems.add(toEntity(lineItemDTO));
        }
        return lineItems;
    }
}


