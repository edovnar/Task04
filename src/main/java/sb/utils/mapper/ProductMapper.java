package sb.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import sb.domain.dto.request.ProductDTORequest;
import sb.domain.entity.Product;
import sb.domain.dto.response.ProductDTOResponse;
import sb.domain.entity.Stock;
import sb.persistence.dao.StockDAO;
import sb.service.SupplierService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    private final SupplierService supplierService;
    private final StockDAO stockDAO;

    public ProductMapper(SupplierService supplierService, StockDAO stockDAO) {
        this.supplierService = supplierService;
        this.stockDAO = stockDAO;
    }


    public ProductDTOResponse toModel(Product product) {

        Integer supplierId = product.getSupplierId();
        String supplierName;

        Stock stock = stockDAO.get(product.getId()).get();

        if(supplierId == null) {
            supplierName = null;
        } else {
            supplierName = supplierService.get(supplierId).getName();
        }

        return new ProductDTOResponse(
                product.getId(),
                product.getName(),
                supplierName,
                stock.getQuantity()

        );
    }

    public List<ProductDTOResponse> allToModel(Page<Product> products) {
        List<ProductDTOResponse> productDTOResponses = new ArrayList<>();
        for(Product product : products) {
            productDTOResponses.add(toModel(product));
        }

        return productDTOResponses;
    }

    public Product toEntity(ProductDTORequest productDTORequest) {
        return new Product(productDTORequest.getName(), productDTORequest.getSupplierId());
    }
}
