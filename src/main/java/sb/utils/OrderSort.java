package sb.utils;

import org.springframework.stereotype.Component;
import sb.domain.dto.response.OrderDTOResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderSort {

    public List<OrderDTOResponse> sort (String sortBy, String status, List<OrderDTOResponse> orders) {

        if(sortBy!=null) {
            if (sortBy.equals("_id")) {
                orders.sort((o1, o2) -> o2.getId() - o1.getId());
            }
            if (sortBy.equals("+id")) {
                orders.sort((o1, o2) -> o1.getId() - o2.getId());
            }
            if (sortBy.equals("_date")) {
                orders.sort((o1, o2) -> o2.getSubmittedAt().compareTo(o1.getSubmittedAt()));
            }
        }

        if (status!=null && (status.equals("true") || status.equals("false"))) {
            orders = orders.stream()
                    .filter(order -> order.getStatus().equals(status))
                    .collect(Collectors.toList());
            return orders;
        }
        return orders;
    }

}
