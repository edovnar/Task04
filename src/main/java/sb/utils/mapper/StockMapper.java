package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.dto.request.StockDTORequest;
import sb.domain.entity.Stock;
import sb.domain.dto.response.StockDTOResponse;
import sb.service.ProductService;

@Component
public class StockMapper {

    private final ProductService productService;

    public StockMapper(ProductService productService) {
        this.productService = productService;
    }

    public StockDTOResponse toModel(Stock stock) {
        return new StockDTOResponse(
                productService.get(stock.getId()).getName(),
                stock.getQuantity()
        );
    }

    public Stock toEntity(StockDTORequest stockDTORequest) {
        return new Stock(
                stockDTORequest.getProductId(),
                stockDTORequest.getQuantity()
        );
    }
}
