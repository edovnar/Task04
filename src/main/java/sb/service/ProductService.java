package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.dto.response.ProductDTOResponse;
import sb.domain.entity.Product;
import sb.domain.entity.Stock;
import sb.persistence.dao.ProductDAO;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.SupplierDAO;
import sb.service.exception.NotFoundException;
import sb.utils.ListToPageConverter;
import sb.utils.mapper.ProductMapper;

import java.util.List;

@Service
@Transactional
public class ProductService {
    
    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;
    @Autowired
    private StockDAO stockDAO;
    @Autowired
    private ProductMapper productMapper;

    public ProductService(ProductDAO productDAO, SupplierDAO supplierDAO) {
        this.productDAO = productDAO;
        this.supplierDAO = supplierDAO;
    }

    public Page<Product> getAll(Pageable pageable) {
        ListToPageConverter<Product> converter = new ListToPageConverter<>();
        return converter.toPage(productDAO.getAll(), pageable);
    }

    public Product get(int id) {
        productDAO.get(id).orElseThrow(() -> new NotFoundException("No product, dear"));
        return productDAO.get(id).get();
    }

    public Product getByStock(int id) {
        return productDAO.getByStock(id)
                .orElseThrow(() -> new NotFoundException("No product on stock"));
    }

    public Product save(Product product) {
//        Supplier supplier = supplierDAO.getByName(product.getSupplierName())
//                .orElseThrow(() -> new NotFoundException("No such supplier"));

//        Product product = productMapper.toEntity(productDTOResponse);

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
