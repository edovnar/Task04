package sb.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
   public NotFoundException(String message) {
      super(message);
   }
}
