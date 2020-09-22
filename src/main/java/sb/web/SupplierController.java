package sb.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sb.domain.dto.request.SupplierDTORequest;
import sb.domain.entity.Supplier;
import sb.utils.mapper.SupplierMapper;
import sb.domain.dto.response.SupplierDTOResponse;
import sb.service.SupplierService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private SupplierService supplierService;
    private SupplierMapper supplierMapper;

    public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }

    @GetMapping
    public List<SupplierDTOResponse> getAll() {
        return supplierMapper.allToModel(supplierService.getAll());
    }

    @GetMapping("/{id}")
    public SupplierDTOResponse get(@PathVariable("id") int id) {
        return supplierMapper.toModel(supplierService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDTOResponse create(@RequestBody @Valid SupplierDTORequest supplierDTORequest) {
        Supplier supplier = supplierMapper.toEntity(null, supplierDTORequest);

        return supplierMapper.toModel(supplierService.save(supplier));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public SupplierDTOResponse update(@PathVariable("id") int id, @RequestBody @Valid SupplierDTORequest supplierDTORequest) {
        Supplier supplier = supplierMapper.toEntity(null, supplierDTORequest);

        return supplierMapper.toModel(supplierService.update(id, supplier));
    }
}
