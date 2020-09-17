package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ProductDTORequest {

    @NotNull(message = "Specify supplier")
    private int supplierId;

    @NotBlank(message = "Name can't be blank")
    private String name;
}
