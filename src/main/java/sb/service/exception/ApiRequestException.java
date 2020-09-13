package sb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiRequestException {

    @ExceptionHandler(value = {CreationException.class})
    public ResponseEntity<Object> handle (CreationException e) {

        BindingResult bindingResult = e.getBindingResult();

        String response ="";
        if(bindingResult != null) {
            for(ObjectError objectError : e.getBindingResult().getAllErrors()) {
                response = response.concat(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
            }
        } else response = e.getMessage();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handle (NotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadOrderException.class})
    private ResponseEntity<Object> handle (BadOrderException e) {
        return new ResponseEntity<>(e.getLineItem() + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {StockNotFoundException.class})
    private ResponseEntity<Object> handle (StockNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
