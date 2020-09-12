package sb.service.exception;

import lombok.Getter;
import sb.domain.entity.LineItem;

@Getter
public class BadOrderException extends RuntimeException {
    private LineItem lineItem;

    public BadOrderException(String message) {
        super(message);
    }

    public BadOrderException (LineItem lineItem) {
        this.lineItem = lineItem;
    }
}
