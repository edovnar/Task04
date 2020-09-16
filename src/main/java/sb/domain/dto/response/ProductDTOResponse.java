package sb.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.Stock;
import sb.domain.entity.Supplier;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTOResponse {

    private int id;
    private String name;
    private String supplierName;
    private Integer stockQuantity;
}
