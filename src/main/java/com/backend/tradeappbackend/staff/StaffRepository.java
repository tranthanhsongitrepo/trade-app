package com.backend.tradeappbackend.staff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff getStaffByUserId(Long id);

    boolean existsByEmail(String email);
}
