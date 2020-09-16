package sb.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
@AllArgsConstructor
public class CreationException extends RuntimeException{

    private BindingResult bindingResult;

    public CreationException(String message) {
        super(message);
    }
}
