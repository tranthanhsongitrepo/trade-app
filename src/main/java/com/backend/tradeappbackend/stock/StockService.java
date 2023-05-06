package com.backend.tradeappbackend.stock;

import com.backend.tradeappbackend.stock.exception.FavoriteStockAlreadyExistsException;
import com.backend.tradeappbackend.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository socketRepository) {
        this.stockRepository = socketRepository;
    }

    public Page<Stock> getStockId(Pageable pageable) {
        return this.stockRepository.findAll(pageable);
    }

    public Page<Stock> getFavoriteStock(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.stockRepository.findFavoriteStockByUserId(user.getUserId(), pageable);
    }

    @Transactional
    public void addFavoriteStock(Stock stock) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (this.stockRepository.findFavoriteStockByUserIdAndStockId(user.getUserId(), stock.getStockId()).size() > 0)
            throw new FavoriteStockAlreadyExistsException();

        this.stockRepository.addFavoriteStockByUserId(user.getUserId(), stock.getStockId());
    }
}
