package sb.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.dto.LineItemDTO;
import sb.domain.dto.UserDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOResponse {

    private int id;
    private String status;
    private Date submittedAt;
    private UserDTO user;

    @NotNull(message = "Can't be empty")
    private List<LineItemDTO> lineItemDTOs;
}
