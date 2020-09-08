package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.LineItem;
import sb.domain.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderModel {

    private int id;
    private User user;
    private List<LineItem> order;
}
