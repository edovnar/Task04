package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @NotBlank(message = "Name can not be blank")
    private String name;

    private Integer supplierId;
}
