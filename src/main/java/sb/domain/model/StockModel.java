package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class StockModel {

    private HashMap<String, Integer> quantity;
}
