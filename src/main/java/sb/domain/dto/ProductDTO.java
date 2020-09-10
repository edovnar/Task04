package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.Stock;
import sb.domain.entity.Supplier;

@Data
@AllArgsConstructor
public class ProductDTO {

    private int id;
    private String name;
    private Supplier supplier;
    private int stockQuantity;
}
