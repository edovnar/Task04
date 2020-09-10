package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LineItemDTO {

    private ProductDTO product;
    private int quantity;
}
