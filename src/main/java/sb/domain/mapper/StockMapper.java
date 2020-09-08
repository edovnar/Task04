package sb.domain.mapper;

import sb.domain.entity.Stock;
import sb.domain.model.StockModel;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;

import java.util.HashMap;

public class StockMapper {

    private StockDAO stockDAO;
    private ProductDAO productDAO;

    public StockMapper(StockDAO stockDAO, ProductDAO productDAO) {
        this.stockDAO = stockDAO;
        this.productDAO =productDAO;
    }

    public StockModel toModel() {
        HashMap<String, Integer> productQuantity = new HashMap<>();
        for(Stock stock : stockDAO.getAll()){
            productQuantity.put(productDAO.getByStock(stock.getId()).getName(),
                    stock.getQuantity());
        }
        return new StockModel(productQuantity);
    }
}
