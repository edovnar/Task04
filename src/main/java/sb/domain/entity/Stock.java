package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private Integer id;

    @PositiveOrZero(message = "Quantity can't be negative")
    private Integer quantity;
}
