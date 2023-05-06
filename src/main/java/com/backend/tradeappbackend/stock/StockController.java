package com.backend.tradeappbackend.stock;

import com.backend.tradeappbackend.stock.dto.StockDTO;
import com.backend.tradeappbackend.stock.response.AddFavoriteStockResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    private final StockService stockService;
    private final ModelMapper modelMapper;


    @Autowired
    public StockController(StockService socketService, ModelMapper modelMapper) {
        this.stockService = socketService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{page}")
    public Collection<Stock> getSocketId(@PathVariable("page") int page) {
        if (page <= 0)
            throw new IllegalArgumentException("Page number must be greater than 0");

        return this.stockService.getStockId(PageRequest.of(page - 1, 10)).getContent();
    }

    @GetMapping("/favorite/{page}")
    public Collection<Stock> getFavoriteSocketId(@PathVariable("page") int page) {
        if (page <= 0)
            throw new IllegalArgumentException("Page number must be greater than 0");

        return this.stockService.getFavoriteStock(PageRequest.of(page - 1, 10)).getContent();
    }

    @PostMapping("/favorite/")
    public ResponseEntity<?> addFavoriteStock(@RequestBody @Validated StockDTO stockDTO) {
        Stock stock = this.modelMapper.map(stockDTO, Stock.class);
        this.stockService.addFavoriteStock(stock);
        return ResponseEntity.ok(new AddFavoriteStockResponse(stock.getStockId()));
    }
}
