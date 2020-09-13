package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {

    private int id;
    private String shipped;
    private Date submittedAt;
    private UserDTO user;
    private List<ProductDTO> products;
}
