package sb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    private Integer id;
    private Integer userId;
    private String name;
    private String address;
    private String payerNumber;
    private String registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
