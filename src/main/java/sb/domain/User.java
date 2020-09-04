package sb.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
public class User extends BaseEntity {

    private String name;
    private String password;
    private String status;
    private String email;
}
