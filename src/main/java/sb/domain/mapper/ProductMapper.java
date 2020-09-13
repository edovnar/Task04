package sb.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.Product;
import sb.domain.dto.ProductDTO;
import sb.persistence.dao.StockDAO;
import sb.persistence.dao.SupplierDAO;
import sb.service.SupplierService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StockDAO stockDAO;


    public ProductDTO toModel(Product product) {

        Integer supplierId = product.getSupplierId();
        String supplierName;

        if(supplierId == null) {
            supplierName = "Supplier doesn't exists anymore";
        } else {
            supplierName = supplierService.get(supplierId).getName();
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                supplierName
        );
    }

    public List<ProductDTO> allToModel(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : products) {
            productDTOs.add(toModel(product));
        }
        return productDTOs;
    }
}
