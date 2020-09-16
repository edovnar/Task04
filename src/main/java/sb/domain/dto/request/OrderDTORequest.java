package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.dto.UserDTO;
import sb.domain.entity.LineItem;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTORequest {

    private int id;
    private String status;
    private Date submittedAt;
    private UserDTO user;

    @NotNull
    private List<LineItem> lineItems;
}
