package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Stock;
import sb.domain.dto.StockDTO;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;

@Component
public class StockMapper {

    private StockDAO stockDAO;
    private ProductDAO productDAO;

    public StockMapper(StockDAO stockDAO, ProductDAO productDAO) {
        this.stockDAO = stockDAO;
        this.productDAO =productDAO;
    }

    public StockDTO toModel(Stock stock) {
        return new StockDTO(
                productDAO.getByStock(stock.getId()).getName(),
                stock.getQuantity()
        );
    }
}
