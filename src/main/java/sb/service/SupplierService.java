package sb.service;

import org.springframework.stereotype.Service;
import sb.domain.Supplier;
import sb.persistence.repository.SupplierRepository;

@Service
public class SupplierService extends GeneralService<Supplier> {

    public SupplierService(SupplierRepository supplierRepository) {
        super(supplierRepository);
    }
}
