package sb.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDTORequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Role must not be blank")
    private String role;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;
}
