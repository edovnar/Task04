package sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="orders")
@Getter @Setter
@ToString
public class Order extends BaseEntity {

    private boolean shipped;
    private int submittedBy;
    private Date submittedAt;

}
