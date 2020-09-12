package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Product;
import sb.domain.dto.ProductDTO;
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

    public ProductDTO toModel(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                supplierDAO.get(product.getSupplierId()).getName(),
                stockDAO.get(product.getId()).getQuantity()
        );
    }
}
