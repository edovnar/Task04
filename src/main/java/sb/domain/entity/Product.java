package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Product extends BaseEntity {

    @NotBlank(message = "Name is required")
    private String name;
    private int supplierId;
}
