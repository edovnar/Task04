package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {

    private int id;
    private UserDTO user;
    private List<LineItemDTO> order;
}
