package sb.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.Stock;
import sb.domain.dto.StockDTO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;
import sb.service.ProductService;

@Component
public class StockMapper {

    @Autowired
    private ProductService productService;


    public StockDTO toModel(Stock stock) {
        return new StockDTO(
                productService.getByStock(stock.getId()).getName(),
                stock.getQuantity()
        );
    }
}
