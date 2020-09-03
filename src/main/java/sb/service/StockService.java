package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.Stock;

@Component
public class StockService extends Service<Stock> {

    public StockService(JpaRepository<Stock, Integer> jpaRepo) {
        super(jpaRepo);
    }
}
