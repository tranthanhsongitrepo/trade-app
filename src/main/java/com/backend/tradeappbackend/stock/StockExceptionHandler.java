package com.backend.tradeappbackend.stock;

import com.backend.tradeappbackend.stock.exception.FavoriteStockAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {
    @ExceptionHandler(value = FavoriteStockAlreadyExistsException.class)
    public ResponseEntity<?> handleFavoriteStockAlreadyExistsException(FavoriteStockAlreadyExistsException e) {
        return ResponseEntity.ok(
                new FavoriteStockAlreadyExistsResponse()
        );
    }
}
