package sb.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.dto.response.ProductDTOResponse;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class LineItemDTO {

    @JsonIgnore
    private int orderId;

    private ProductDTOResponse product;
    private int quantity;
}
