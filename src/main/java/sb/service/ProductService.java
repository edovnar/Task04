package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.entity.Product;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.SupplierDAO;
import sb.service.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
public class ProductService {
    
    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;

    public ProductService(ProductDAO productDAO, SupplierDAO supplierDAO) {
        this.productDAO = productDAO;
        this.supplierDAO = supplierDAO;
    }

    public List<Product> getAll() {
        return productDAO.getAll();
    }

    public Product get(int id) {
        productDAO.get(id).orElseThrow(() -> new NotFoundException("No product, dear"));
        return productDAO.get(id).get();
    }

    public Product getByStock(int id) {
        return productDAO.getByStock(id)
                .orElseThrow(() -> new NotFoundException("No product on stock"));
    }

    public void save(Product product) {
        supplierDAO.get(product.getSupplierId())
                .orElseThrow(() -> new NotFoundException("No such supplier"));
        productDAO.post(product);
    }

    public void update(int id, Product product) {
        productDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such prduct"));
        productDAO.update(id, product);
    }

    public  void delete(int id) {
        productDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        productDAO.delete(id);
    }
}
