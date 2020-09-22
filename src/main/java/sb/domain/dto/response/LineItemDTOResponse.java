package sb.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineItemDTOResponse {

    private ProductDTOResponse product;
    private int quantity;
}
