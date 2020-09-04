package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.Stock;
import sb.persistence.repository.StockRepository;

@Service
public class StockService extends GeneralService<Stock> {

    public StockService(StockRepository stockRepository) {
        super(stockRepository);
    }
}
