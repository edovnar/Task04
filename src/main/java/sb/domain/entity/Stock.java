package sb.domain.entity;

import lombok.Data;
import javax.validation.constraints.Positive;

@Data
public class Stock extends BaseEntity {

    @Positive (message = "Quantity should be positive")
    private Integer quantity;
}
