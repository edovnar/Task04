package sb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.domain.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
