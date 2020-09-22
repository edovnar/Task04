package sb.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTOResponse {

    private Integer id;
    private String name;
    private String supplierName;
    private Integer stockQuantity;
}
