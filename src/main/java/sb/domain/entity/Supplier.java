package sb.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    @Positive
    private Integer userId;

    @NotBlank(message = "Name is required")
    private String name;

    private String address;

    @Range(min = 9999, max = 1000000000, message = "Payer number's length should be between 5 and 9")
    private Integer payerNumber;

    @NotNull(message = "Certificate can't be null")
    @Positive
    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;
}
