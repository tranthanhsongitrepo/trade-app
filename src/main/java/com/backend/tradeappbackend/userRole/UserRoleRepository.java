package com.backend.tradeappbackend.userRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findRoleByRoleName(String roleName);

    @Query("SELECT ur FROM UserRole ur JOIN ur.userWithRole u JOIN FETCH ur.userAuthorities ua WHERE u.userId = :userId")
    List<UserRole> findUserRolesByUserId(Long userId);
}
