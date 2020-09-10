package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class StockDTO {

    private String productName;
    private int quantity;
}
