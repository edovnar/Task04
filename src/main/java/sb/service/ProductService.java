package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.Product;

@Component
public class ProductService extends Service<Product> {

    public ProductService(JpaRepository<Product, Integer> jpaRepo) {
        super(jpaRepo);
    }
}
