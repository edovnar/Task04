package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sb.domain.Supplier;

@Component
public class SupplierService extends Service<Supplier> {

    public SupplierService(JpaRepository<Supplier, Integer> jpaRepo) {
        super(jpaRepo);
    }
}
