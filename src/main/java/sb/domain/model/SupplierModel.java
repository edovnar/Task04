package sb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.domain.entity.User;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SupplierModel {

    private User user;
    private String name;
    private String address;
    private Date registrationDate;
    private String phoneNumber;
}
