package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class User extends BaseEntity {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    private String password;
    
    private String role;
    private String email;
}
