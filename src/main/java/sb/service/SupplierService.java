package sb.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserDAO userDAO;

    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
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
        return supplierDAO.getByUser(id);
    }

    public void save(Supplier supplier) {
        Integer userId = supplier.getUserId();
        if (userId != null && userDAO.get(userId).isPresent()){
            supplierDAO.post(supplier);
        } else throw new NotFoundException("Representative is not found");

    }

    public  void delete(int id) {
        supplierDAO.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        supplierDAO.delete(id);
    }

    public void update(int id, Supplier supplier){
        supplierDAO.get(id)
                .orElseThrow(() -> new NotFoundException("No such supplier"));
        supplierDAO.update(id, supplier);
    }
}
