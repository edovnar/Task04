package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.dto.response.UserDTOResponse;
import sb.domain.entity.LineItem;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTORequest {

    private String status;
    private Date submittedAt;
    private UserDTOResponse user;

    @NotNull(message = "Add products to the order")
    private List<LineItem> lineItems;
}
