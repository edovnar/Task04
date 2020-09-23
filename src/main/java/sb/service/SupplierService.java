package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.entity.Supplier;
import sb.persistence.dao.SupplierDAO;
import sb.persistence.dao.UserDAO;
import sb.service.exception.NotFoundException;

import java.util.List;

@Service
public class SupplierService {

    private SupplierDAO supplierDAO;
    private UserDAO userDAO;

    public SupplierService(SupplierDAO supplierDAO, UserDAO userDAO) {
        this.supplierDAO = supplierDAO;
        this.userDAO = userDAO;
    }

    public List<Supplier> getAll() {
        return supplierDAO.getAll();
    }

    public Supplier get(int id) {
        if(supplierDAO.get(id).isEmpty()) {
            throw new NotFoundException("No such supplier");
        }

        return supplierDAO.get(id).get();
    }

    public List<Supplier> getByUser(int id) {
        return supplierDAO.getByUserId(id);
    }

    public Supplier save(Supplier supplier) {
        int supplierId = supplierDAO.create(supplier);

        return supplierDAO.get(supplierId).get();
    }

    public  void delete(int id) {
        supplierDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        supplierDAO.delete(id);
    }

    public Supplier update(int id, Supplier supplier){
        supplierDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such supplier"));

        if(supplier.getUserId() != null && userDAO.get(supplier.getUserId()).isEmpty()) {
           throw new NotFoundException("No such representative");
        }

        supplierDAO.update(id, supplier);

        return supplierDAO.get(id).get();
    }
}
