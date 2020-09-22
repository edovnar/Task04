package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.domain.dto.request.SupplierDTORequest;
import sb.domain.entity.Supplier;
import sb.domain.dto.response.SupplierDTOResponse;
import sb.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {

    private final UserService userService;

    public SupplierMapper(UserService userService) {
        this.userService = userService;
    }

    public SupplierDTOResponse toModel(Supplier supplier) {
        String supplierName = "";
        if(supplier.getUserId() != null) {
            supplierName = userService.get(supplier.getUserId()).getName();
        } else {
            supplierName = "Have no representative yet";
        }

        return new SupplierDTOResponse(
                supplier.getId(),
                supplierName,
                supplier.getName(),
                supplier.getAddress(),
                supplier.getRegistrationDate(),
                supplier.getPhoneNumber()
        );
    }

    public List<SupplierDTOResponse> allToModel(List<Supplier> suppliers) {

        List<SupplierDTOResponse> DTOs= new ArrayList<>();

        for (Supplier supplier : suppliers) {
            DTOs.add(toModel(supplier));
        }

        return DTOs;
    }

    public Supplier toEntity(Integer id, SupplierDTORequest supplierDTORequest) {
        return new Supplier(
                id,
                supplierDTORequest.getUserId(),
                supplierDTORequest.getName(),
                supplierDTORequest.getAddress(),
                supplierDTORequest.getPayerNumber(),
                supplierDTORequest.getRegistrationCertificateNumber(),
                supplierDTORequest.getRegistrationDate(),
                supplierDTORequest.getPhoneNumber()
        );
    }
}
