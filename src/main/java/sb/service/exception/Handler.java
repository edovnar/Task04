package sb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sb.utils.Message;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Message> handle (MethodArgumentNotValidException e) {

        String message = "";
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            message = message + String.format(
                        "%s: %s ",
                        objectError.getObjectName(), objectError.getDefaultMessage()
                );
            }
        return new ResponseEntity<>(new Message(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreationException.class)
    public ResponseEntity<Message> handle (CreationException e) {

        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Message> handle (NotFoundException e){

        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadOrderException.class)
    private ResponseEntity<Message> handle (BadOrderException e) {
        Message message = new Message(e.getMessage());

        message.setMessage(String.format(
                "%s: (productId = %s, quantity = %s)",
                message.getMessage(), e.getLineItem().getProductId(), e.getLineItem().getQuantity())
        );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Message> handle (MethodArgumentTypeMismatchException e) {

        return new ResponseEntity<>(new Message("Invalid path variable"), HttpStatus.BAD_REQUEST);
    }
}
