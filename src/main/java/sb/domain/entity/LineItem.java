package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private Integer orderId;
    private Integer productId;
    private int quantity;

    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LineItem)) return false;
        LineItem other = (LineItem) o;

        if (!this.getProductId().equals(other.getProductId())) return false;
        return true;
    }
}
