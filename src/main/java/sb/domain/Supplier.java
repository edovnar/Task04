package sb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="suppliers")
@Getter
@Setter
@ToString
public class Supplier extends BaseEntity {

    private String name;
    private String address;
    private int payerNumber;
    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
