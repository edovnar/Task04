package sb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Stock;
import sb.service.StockService;
import sb.service.exception.CreationException;

import javax.validation.Valid;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping
    public Stock updateStock (@RequestBody @Valid Stock stock, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreationException(bindingResult);
        }

        return stockService.update(stock);
    }
}
