package sb.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.dto.request.ProductDTORequest;
import sb.domain.entity.Product;
import sb.utils.mapper.ProductMapper;
import sb.domain.dto.response.ProductDTOResponse;
import sb.service.ProductService;
import sb.service.exception.CreationException;

import javax.validation.Valid;
import java.util.List;

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
    public List<ProductDTOResponse> getAll(@PageableDefault(sort = ("name")) Pageable pageable) {
       return productMapper.allToModel(productService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ProductDTOResponse get(@PathVariable("id") int id) {
        return productMapper.toModel(productService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse create(@RequestBody @Valid ProductDTORequest productDTORequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new CreationException(bindingResult);
        }
        Product product = productService.save(
                productMapper.toEntity(productDTORequest)
        );

        return productMapper.toModel(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductDTOResponse update(@PathVariable("id") int id, @RequestBody @Valid ProductDTORequest productDTORequest,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreationException(bindingResult);
        }
        Product product = productMapper.toEntity(productDTORequest);

        return productMapper.toModel(productService.update(id, product));
    }
}
