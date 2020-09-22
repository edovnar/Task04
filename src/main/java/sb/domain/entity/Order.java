package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;
    private String status;
    private Integer submittedBy;
    private Date submittedAt;
    private Date updatedAt;
}
