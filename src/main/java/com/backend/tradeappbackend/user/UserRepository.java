package com.backend.tradeappbackend.user;

import com.backend.tradeappbackend.stock.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserId(Long userId);

    User getUserByProviderId(String providerId);

    User findUserByEmail(String email);

    boolean existsByUserId(Long userId);

    boolean existsByEmail(String email);

    @Query("SELECT u.email FROM User u JOIN u.authenticationRefreshToken WHERE u.authenticationRefreshToken.hashedRefreshTokenString = ?1")
    String findUserEmailByAuthenticationRefreshTokenRefreshTokenString(String hashedToken);

    @Modifying
    @Query("UPDATE User u SET u.currentAccessTokenUUID = :loggedInDeviceId WHERE u.userId = :userId")
    void updateLoggedInDeviceIdByUserId(String loggedInDeviceId, Long userId);

    @Modifying
    @Query("UPDATE User u SET u.currentAccessTokenUUID = :loggedInDeviceId WHERE u.email = :email")
    void updateLoggedInDeviceIdByUserEmail(String loggedInDeviceId, String email);

    void deleteByUserId(Long userId);
}
