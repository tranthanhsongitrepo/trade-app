package com.backend.tradeappbackend.userAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority findUserAuthoritiesByUserAuthorityName(String roleName);

    @Query("SELECT ua FROM UserAuthority ua JOIN ua.usersWithAuthority u WHERE u.userId = :userId")
    List<UserAuthority> findUserAuthoritiesByUserId(Long userId);
}
