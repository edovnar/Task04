package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.LineItem;
import sb.domain.dto.response.LineItemDTOResponse;
import sb.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineItemMapper {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public LineItemMapper(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    public LineItemDTOResponse toModel(LineItem lineItem) {
           return new LineItemDTOResponse(
                   productMapper.toModel(
                           productService.get(lineItem.getProductId())
                   ),
                   lineItem.getQuantity()
           );
    }

    public List<LineItemDTOResponse> allToModel(List<LineItem> lineItems) {
        List<LineItemDTOResponse> lineItemDTOResponses = new ArrayList<>();
        for (LineItem lineItem : lineItems) {
            lineItemDTOResponses.add(toModel(lineItem));
        }

        return lineItemDTOResponses;
    }
}


