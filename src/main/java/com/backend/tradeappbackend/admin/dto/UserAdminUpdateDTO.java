package com.backend.tradeappbackend.admin.dto;

import com.backend.tradeappbackend.user.AuthProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminUpdateDTO {
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private AuthProvider provider;
    private String providerId;

    private List<UserAuthorityDTO> userAuthorities;

    @NotNull
    @Size(min = 1)
    private List<UserRoleDTO> userRoles;
}
