package sb.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    private String name;
    private String address;
    private int payerNumber;
    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
