package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrderModel {

    private UserModel user;
    private LineItemModel order;
}
