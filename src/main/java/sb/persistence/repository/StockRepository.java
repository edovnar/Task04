package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}