package sb.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class StockDTOResponse {

    private String productName;

    @PositiveOrZero(message = "Quantity can't be negative")
    private int quantity;
}
