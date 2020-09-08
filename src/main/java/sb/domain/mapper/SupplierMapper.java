package sb.domain.mapper;

import org.springframework.stereotype.Component;
import sb.domain.entity.Supplier;
import sb.domain.model.SupplierModel;
import sb.persistence.dao.UserDAO;

@Component
public class SupplierMapper {

    private final UserDAO userDAO;

    public SupplierMapper(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SupplierModel toModel(Supplier supplier) {
        return new SupplierModel(userDAO.get(supplier.getUserId()),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getRegistrationDate(),
                supplier.getPhoneNumber());
    }
}
