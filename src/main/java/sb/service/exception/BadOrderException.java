package sb.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.LineItem;

@Data
@AllArgsConstructor
public class BadOrderException extends RuntimeException {
    private LineItem lineItem;
    private String message;
}
