package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.entity.Stock;
import sb.persistence.dao.StockDAO;
import sb.service.exception.NotFoundException;

@Service
public class StockService {

    private final StockDAO stockDAO;

    public StockService(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public Stock update(Stock stock) {

        stockDAO.update(stock);

        return stockDAO.get(stock.getId())
                .orElseThrow(() -> new NotFoundException("No such stock"));
    }
}
