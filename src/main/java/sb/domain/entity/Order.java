package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Order extends BaseEntity {

    @NotBlank(message = "Status is required")
    private String shipped;
    private int submittedBy;
    private Date submittedAt;
    private Date updatedAt;
}
