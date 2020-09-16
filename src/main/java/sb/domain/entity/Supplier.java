package sb.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Data
public class Supplier extends BaseEntity {

    private Integer userId;

    @NotBlank
    private String name;

    private String address;

    @Range(min = 9999, max = 1000000000)
    private Integer payerNumber;

    @NotNull
    @Positive
    private int registrationCertificateNumber;

    private Date registrationDate;
    private String phoneNumber;
}
