package sb.domain.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    private int userId;

    @NotBlank(message = "Name is required")
    private String name;

    private String address;

    @NotBlank(message = "Payer number is required")
    @Size(min = 5, max = 15, message = "Number should be between 5 and 15")
    private int payerNumber;

    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
