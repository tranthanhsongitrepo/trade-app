package com.backend.tradeappbackend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorityDTO {
    private Long userAuthorityId;
    private String userAuthorityName;
    private Long usersWithAuthority;
}
