package sb.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sb.domain.entity.Supplier;
import sb.persistence.dao.SupplierDAO;
import sb.persistence.dao.UserDAO;
import sb.service.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
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

    public Supplier getByName(String name) {
        return supplierDAO.getByName(name)
                .orElseThrow(() -> new NotFoundException("No such supplier"));
    }

    public List<Supplier> getByUser(int id) {
        return supplierDAO.getByUser(id);
    }

    public Supplier save(Supplier supplier) {

        int supplierId = supplierDAO.post(supplier);

        return supplierDAO.get(supplierId).get();
    }

    public  void delete(int id) {
        supplierDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        supplierDAO.delete(id);
    }

    public void update(int id, Supplier supplier){
        supplierDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such supplier"));
        supplierDAO.update(id, supplier);
    }
}
