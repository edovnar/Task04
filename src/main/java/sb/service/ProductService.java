package sb.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import sb.domain.entity.Product;
import sb.domain.entity.Stock;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.SupplierDAO;
import sb.service.exception.NotFoundException;

import java.util.List;

@Service
public class ProductService {

    private ProductDAO productDAO;
    private StockDAO stockDAO;
    private SupplierDAO supplierDAO;

    public ProductService(ProductDAO productDAO, StockDAO stockDAO, SupplierDAO supplierDAO) {
        this.productDAO = productDAO;
        this.stockDAO = stockDAO;
        this.supplierDAO = supplierDAO;
    }

    public List<Product> getAll(Pageable pageable) {
        return productDAO.getAll(pageable);
    }

    public Product get(int id) {

        return productDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No product, dear"));
    }

    public Product save(Product product) {
        supplierDAO.get(product.getSupplierId())
                .orElseThrow(() -> new NotFoundException("No such supplier"));

        int productId = productDAO.post(product);
        stockDAO.post(new Stock(productId, 1));

        return productDAO.get(productId).get();
    }

    public Product update(int id, Product product) {
        productDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such product"));

        productDAO.update(id, product);

        return productDAO.get(id).get();
    }

    public void delete(int id) {
        productDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        productDAO.delete(id);
        stockDAO.delete(id);
    }
}
