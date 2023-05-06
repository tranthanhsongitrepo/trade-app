package com.backend.tradeappbackend.stock.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class AddFavoriteStockResponse extends GenericResponse {
    public AddFavoriteStockResponse(Long stockId) {
        super(200, "Add favorite stock with ID %d successfully".formatted(stockId));
    }
}
