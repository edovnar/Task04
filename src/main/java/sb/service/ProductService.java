package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.Product;
import sb.persistence.repository.ProductRepository;

@Service
public class ProductService extends GeneralService<Product> {

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }
}
