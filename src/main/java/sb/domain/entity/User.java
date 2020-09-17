package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User extends BaseEntity {

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
