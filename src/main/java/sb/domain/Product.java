package sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="products")
@Getter
@Setter
@ToString
public class Product extends BaseEntity {

    private String name;
}
