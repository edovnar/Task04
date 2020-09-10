package sb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SupplierDTO {

    private String userName;
    private String name;
    private String address;
    private Date registrationDate;
    private String phoneNumber;
}
