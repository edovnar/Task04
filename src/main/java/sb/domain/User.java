package sb.domain;

import lombok.Data;

@Data
public class User extends BaseEntity {

    private String name;
    private String password;
    private String role;
    private String email;
}
