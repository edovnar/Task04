package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class LineItemModel {

    private HashMap<String, Integer> productQuantity;
}
