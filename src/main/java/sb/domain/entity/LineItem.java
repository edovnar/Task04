package sb.domain.entity;

import lombok.Data;
import javax.validation.constraints.Positive;

@Data
public class LineItem extends BaseEntity{

    private int orderId;
    private int productId;

    @Positive
    private int quantity;
}
