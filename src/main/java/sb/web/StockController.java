package sb.web;

import org.springframework.web.bind.annotation.*;
import sb.domain.dto.request.StockDTORequest;
import sb.domain.dto.response.StockDTOResponse;
import sb.domain.entity.Stock;
import sb.service.StockService;
import sb.utils.mapper.StockMapper;

import javax.validation.Valid;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;
    private StockMapper stockMapper;

    public StockController(StockService stockService, StockMapper stockMapper) {
        this.stockService = stockService;
        this.stockMapper = stockMapper;
    }

    @PutMapping
    public StockDTOResponse updateStock (@RequestBody @Valid StockDTORequest stockDTORequest) {
        Stock stock = stockMapper.toEntity(stockDTORequest);

        return stockMapper.toModel(
                stockService.update(stock)
        );
    }
}
