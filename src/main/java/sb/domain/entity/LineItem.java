package sb.domain.entity;

import lombok.Data;

@Data
public class LineItem extends BaseEntity{

    private int orderId;
    private int productId;
    private int quantity;
}
