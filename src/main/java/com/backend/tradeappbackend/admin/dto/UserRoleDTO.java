package com.backend.tradeappbackend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Long userRoleId;
    private String roleName;

    private List<UserAuthorityDTO> usersWithAuthority;

    private Long usersWithRole;
}
