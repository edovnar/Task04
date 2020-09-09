package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Product;
import sb.domain.model.ProductModel;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.SupplierDAO;

@Component
public class ProductMapper {

    private SupplierDAO supplierDAO;
    private StockDAO stockDAO;

    public ProductMapper(SupplierDAO supplierDAO,StockDAO stockDAO) {
        this.supplierDAO = supplierDAO;
        this.stockDAO = stockDAO;
    }

    public ProductModel toModel(Product product) {
        return new ProductModel(product.getName(),
                supplierDAO.get(product.getSupplierId()),
                stockDAO.get(product.getStockId()));
    }
}
