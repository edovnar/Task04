package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    @Email
    @NotBlank
    private String email;
}
