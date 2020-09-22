package sb.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOResponse {

    private Integer id;
    private String status;
    private Date submittedAt;
    private UserDTOResponse user;
    private List<LineItemDTOResponse> lineItemDTOResponses;
}
