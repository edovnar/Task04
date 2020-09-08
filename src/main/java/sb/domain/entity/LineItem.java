package sb.domain.entity;

import lombok.Data;

@Data
public class LineItem {

    private int orderId;
    private int productId;
    private int quantity;
}
