package sb.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SupplierDTOResponse {

    private Integer id;
    private String userName;
    private String name;
    private String address;
    private Date registrationDate;
    private String phoneNumber;
}
