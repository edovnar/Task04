package sb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class Handler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreationException.class)
    public ResponseEntity<Message> handle (CreationException e) {

        BindingResult bindingResult = e.getBindingResult();
        Message message = new Message(e.getMessage());

        if (bindingResult != null) {
            for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
                message.setMessage( String.format(
                        "%s: %s",
                        objectError.getObjectName(), objectError.getDefaultMessage())
                );
            }
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Message> handle (NotFoundException e){

        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadOrderException.class)
    private ResponseEntity<Message> handle (BadOrderException e) {
        Message message = new Message(e.getMessage());

        message.setMessage(String.format(
                "%s: (productId = %s, quantity = %s)",
                message.getMessage(), e.getLineItem().getProductId(), e.getLineItem().getQuantity())
        );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Message> handle (MethodArgumentTypeMismatchException e) {

        return new ResponseEntity<>(new Message("Invalid path variable"), HttpStatus.BAD_REQUEST);
    }
}
