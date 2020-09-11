package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
public class Order extends BaseEntity {

    @NotBlank(message = "Status is required")
    private String shipped;
    private int submittedBy;
    private Date submittedAt;
    private Date updatedAt;
}
