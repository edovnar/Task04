package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.Stock;
import sb.domain.entity.Supplier;

@Data
@AllArgsConstructor
public class ProductModel {

    private String name;
    private Supplier supplier;
    private Stock stock;
}
