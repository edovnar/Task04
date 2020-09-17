package sb.web;

import org.springframework.web.bind.annotation.*;
import sb.domain.entity.Stock;
import sb.service.StockService;

import javax.validation.Valid;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping
    public Stock updateStock (@RequestBody @Valid Stock stock) {
        return stockService.update(stock);
    }
}
