package sb.domain.entity;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {

    @Id
    private int id;
}
