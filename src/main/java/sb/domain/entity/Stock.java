package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class Stock extends BaseEntity {

    @Min(value = 0, message = "Can not be zero or negative")
    private int quantity;
}
