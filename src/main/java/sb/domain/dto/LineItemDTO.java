package sb.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class LineItemDTO {

    @JsonIgnore
    private int orderId;
    private ProductDTO product;

    @Positive
    private int quantity;
}
