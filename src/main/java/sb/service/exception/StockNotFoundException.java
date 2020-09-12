package sb.service.exception;

import lombok.Getter;

@Getter
public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(String message) {
        super(message);
    }
}
