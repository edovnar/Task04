package sb.web;

import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Supplier;
import sb.domain.mapper.SupplierMapper;
import sb.domain.dto.OrderDTO;
import sb.domain.dto.SupplierDTO;
import sb.persistence.dao.SupplierDAO;
import sb.service.SupplierService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private SupplierService supplierService;
    private SupplierMapper supplierMapper;
    private SupplierDAO supplierDAO;

    public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper, SupplierDAO supplierDAO) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
        this.supplierDAO = supplierDAO;
    }

    @GetMapping
    public List<SupplierDTO> getAll() {
        return supplierService.getAll().stream()
                .map((supplierMapper::toModel))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SupplierDTO get(@PathVariable("id") int id) {
        return supplierMapper.toModel(supplierService.get(id));
    }

    @PostMapping
    public void create(@RequestBody @Valid Supplier supplier){
        supplierService.save(supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Supplier supplier) {
        supplierService.update(supplier);
    }
}
