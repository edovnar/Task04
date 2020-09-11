package sb.service.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CreationException extends RuntimeException{

    private BindingResult bindingResult;
    private String message;

    public CreationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public CreationException(String message) {
        this.message = message;
    }
}
