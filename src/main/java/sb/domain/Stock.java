package sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="stocks")
@Getter
@Setter
@ToString
public class Stock extends BaseEntity {

    private int quantity;
}
