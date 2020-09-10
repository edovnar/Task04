package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class LineItem extends BaseEntity{

    private int orderId;
    private int productId;

    @Min(value = 1, message = "Can't be zero or negative")
    private int quantity;
}
