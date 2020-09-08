package sb.domain.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    private int userId;
    private String name;
    private String address;
    private int payerNumber;
    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
