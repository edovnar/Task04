package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.domain.entity.Product;
import sb.persistence.dao.ProductDAO;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductDAO productDAO;

    public List<Product> getAll() {
        return productDAO.getAll();
    }

    public Optional<Product> get(int id) {
        return productDAO.get(id);
    }

    public Product getByStock(int id) {
        return productDAO.getByStock(id);
    }

    public void save(Product product) {
        productDAO.post(product);
    }

    public void update(Product product) {
        productDAO.update(product);
    }

    public  void delete(int id) {
        productDAO.delete(id);
    }
}
