package sb.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.domain.entity.Stock;
import sb.persistence.dao.StockDAO;
import sb.service.exception.NotFoundException;

@Service
public class StockService {

    @Autowired
    private StockDAO stockDAO;

    public Stock update(Stock stock) {

        stockDAO.update(stock);

        return stockDAO.get(stock.getId())
                .orElseThrow(() -> new NotFoundException("No such stock"));
    }
}
