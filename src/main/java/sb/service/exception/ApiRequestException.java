package sb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sb.domain.entity.LineItem;

@ControllerAdvice
public class ApiRequestException {

    @ExceptionHandler(value = {CreationException.class})
    public ResponseEntity<Object> handle (CreationException e) {

        BindingResult bindingResult = e.getBindingResult();

        String response ="";
        if(bindingResult!=null) {
            for(ObjectError objectError : e.getBindingResult().getAllErrors()) {
                response = response.concat(objectError.getObjectName() + ": "
                        + objectError.getDefaultMessage() + " "
                        + e.getMessage());
            }
        } else response = e.getMessage();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handle (UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadOrderException.class})
    private ResponseEntity<LineItem> handle (BadOrderException e) {
        return new ResponseEntity<>(e.getLineItem(), HttpStatus.BAD_REQUEST);
    }
}
