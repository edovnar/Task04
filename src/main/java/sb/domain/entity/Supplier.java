package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    private Integer userId;

    @NotBlank(message = "Name can't be blank")
    private String name;

    private String address;

    @NotBlank(message = "Payer number is required")
    @Size(min = 5, max = 15, message = "Length must be between 5 and 15")
    private String payerNumber;

    @NotBlank(message = "Registration certificate number is required")
    @Size(min = 5, max = 15, message = "Length must be between 5 and 15")
    private String registrationCertificateNumber;

    private Date registrationDate;
    private String phoneNumber;
}
