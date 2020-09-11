package sb.web;

import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Product;
import sb.domain.mapper.ProductMapper;
import sb.domain.dto.ProductDTO;
import sb.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll().stream()
                .map((productMapper::toModel))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable("id") int id) {
        return productMapper.toModel(productService.get(id));
    }

    @PostMapping
    public void create(@RequestBody @Valid Product product){
        productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Product product) {
        productService.update(product);
    }

}
