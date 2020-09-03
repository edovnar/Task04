package sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dom4j.tree.BaseElement;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    private String name;
    private String password;
    private String status;
    private String email;
}
