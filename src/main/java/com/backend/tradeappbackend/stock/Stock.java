package com.backend.tradeappbackend.stock;

import com.backend.tradeappbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private String socketId;

    private String nameShort;

    private String nameFull;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_user_favorite_stock",
            joinColumns = @JoinColumn(name = "stock_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"stock_id", "user_id"})
    )
    private List<User> usersWithFavoriteStock;
}
