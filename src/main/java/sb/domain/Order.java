package sb.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order extends BaseEntity {

    private String shipped;
    private int submittedBy;
    private Date submittedAt;
    private Date updatedAt;
}
