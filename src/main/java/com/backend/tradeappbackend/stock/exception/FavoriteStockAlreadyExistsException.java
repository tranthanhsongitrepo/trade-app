package com.backend.tradeappbackend.stock.exception;

public class FavoriteStockAlreadyExistsException extends RuntimeException {
    public FavoriteStockAlreadyExistsException() {
        super("Stock already added to favorite");
    }
}
