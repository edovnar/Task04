package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.domain.entity.Supplier;
import sb.persistence.dao.SupplierDAO;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierDAO supplierDAO;

    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public List<Supplier> getAll() {
        return supplierDAO.getAll();
    }

    public Supplier get(int id) {
        return supplierDAO.get(id);
    }

    public List<Supplier> getByUser(int id) {
        return supplierDAO.getByUser(id);
    }

    public void save(Supplier supplier) {
        supplierDAO.post(supplier);
    }

    public  void delete(int id) {
        supplierDAO.delete(id);
    }

    public void update(Supplier supplier){
        supplierDAO.update(supplier);
    }
}
