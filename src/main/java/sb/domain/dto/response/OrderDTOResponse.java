package sb.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTOResponse {

    private Integer id;
    private String status;
    private Date submittedAt;
    private Date updatedAt;
    private UserDTOResponse user;
    private List<LineItemDTOResponse> lineItemDTOResponses;
}
