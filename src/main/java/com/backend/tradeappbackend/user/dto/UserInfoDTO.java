package com.backend.tradeappbackend.user.dto;

import com.backend.tradeappbackend.userAuthority.UserAuthority;
import com.backend.tradeappbackend.userRole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<UserRole> userRoles = new ArrayList<>();
    private List<UserAuthority> userAuthorities = new ArrayList<>();
}
