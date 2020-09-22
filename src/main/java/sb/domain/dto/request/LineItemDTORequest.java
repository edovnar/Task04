package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class LineItemDTORequest {

    private Integer productId;

    @Positive(message = "Quantity must be positive")
    private int quantity;
}
