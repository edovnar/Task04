package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
