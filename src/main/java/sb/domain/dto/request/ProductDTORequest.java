package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTORequest {

    private int supplierId;
    private String name;
}
