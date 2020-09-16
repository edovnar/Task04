package sb.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    @JsonIgnore
    private Integer orderId;
    private Integer productId;

    @Positive
    private int quantity;

    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LineItem)) return false;
        LineItem other = (LineItem) o;

        if (!this.getProductId().equals(other.getProductId())) return false;
        return true;
    }
}
