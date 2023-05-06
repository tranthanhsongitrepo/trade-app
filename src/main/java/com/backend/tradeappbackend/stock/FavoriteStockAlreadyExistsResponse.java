package com.backend.tradeappbackend.stock;

import com.backend.tradeappbackend.response.GenericResponse;

public class FavoriteStockAlreadyExistsResponse extends GenericResponse {
    public FavoriteStockAlreadyExistsResponse() {
        super(400, "Stock already added to favorite");
    }
}
