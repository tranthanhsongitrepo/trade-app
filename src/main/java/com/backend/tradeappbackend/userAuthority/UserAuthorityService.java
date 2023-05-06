package com.backend.tradeappbackend.userAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorityService {
    private final UserAuthorityRepository applicationUserRoleRepository;

    @Autowired
    public UserAuthorityService(UserAuthorityRepository applicationUserRoleRepository) {
        this.applicationUserRoleRepository = applicationUserRoleRepository;
    }

    public UserAuthority findUserAuthoritiesByUserAuthorityName(String roleName) {
        return this.applicationUserRoleRepository.findUserAuthoritiesByUserAuthorityName(roleName);
    }
}
