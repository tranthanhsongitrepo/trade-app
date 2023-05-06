package com.backend.tradeappbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    //    Page<SocketId> findAll(Pageable pageable);
    @Query("SELECT s FROM Stock s JOIN s.usersWithFavoriteStock u WHERE u.userId = :userId")
    Page<Stock> findFavoriteStockByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO trade_app.tbl_user_favorite_stock(stock_id, user_id) VALUES (:userId, :stockId)", nativeQuery = true)
    void addFavoriteStockByUserId(Long userId, Long stockId);

    @Query("SELECT s FROM Stock s JOIN s.usersWithFavoriteStock u WHERE u.userId = :userId AND s.stockId = :stockId")
    Collection<Object> findFavoriteStockByUserIdAndStockId(Long userId, Long stockId);
}
