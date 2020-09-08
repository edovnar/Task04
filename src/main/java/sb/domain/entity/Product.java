package sb.domain.entity;

import lombok.Data;

@Data
public class Product extends BaseEntity {

    private String name;
    private int supplierId;
    private int stockId;
}
