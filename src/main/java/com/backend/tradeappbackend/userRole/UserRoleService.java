package com.backend.tradeappbackend.userRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRepository) {
        this.userRoleRepository = userRepository;
    }

    public UserRole findRoleByRoleName(String roleStaff) {
        return this.userRoleRepository.findRoleByRoleName(roleStaff);
    }
}
