package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @NotBlank
    private String status;

    private Integer submittedBy;
    private Date submittedAt;
    private Date updatedAt;
}
