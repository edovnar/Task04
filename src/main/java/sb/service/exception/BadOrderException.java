package sb.service.exception;

import lombok.Getter;
import sb.domain.entity.LineItem;

@Getter
public class BadOrderException extends RuntimeException {
    private LineItem lineItem;
    private String message;

    public BadOrderException (LineItem lineItem, String message){
        this.message = message;
        this.lineItem = lineItem;
    }
}
