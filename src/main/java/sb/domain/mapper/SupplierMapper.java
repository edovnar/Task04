package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Supplier;
import sb.domain.dto.SupplierDTO;
import sb.persistence.dao.UserDAO;

@Component
public class SupplierMapper {

    private final UserDAO userDAO;

    public SupplierMapper(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SupplierDTO toModel(Supplier supplier) {
        return new SupplierDTO(
                userDAO.get(supplier.getUserId()).getName(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getRegistrationDate(),
                supplier.getPhoneNumber()
        );
    }
}
