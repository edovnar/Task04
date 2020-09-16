package sb.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.Supplier;
import sb.domain.dto.SupplierDTO;
import sb.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {

    private final UserService userService;

    public SupplierMapper(UserService userService) {
        this.userService = userService;
    }

    public SupplierDTO toModel(Supplier supplier) {
        String supplierName = "";
        if(supplier.getUserId() != null) {
            supplierName = userService.get(supplier.getUserId()).getName();
        } else {
            supplierName = "Have no representative yet";
        }

        return new SupplierDTO(
                supplier.getId(),
                supplierName,
                supplier.getName(),
                supplier.getAddress(),
                supplier.getRegistrationDate(),
                supplier.getPhoneNumber()
        );
    }

    public List<SupplierDTO> allToModel(List<Supplier> suppliers) {

        List<SupplierDTO> DTOs= new ArrayList<>();

        for (Supplier supplier : suppliers) {
            DTOs.add(toModel(supplier));
        }

        return DTOs;
    }
}
