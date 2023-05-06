package com.backend.tradeappbackend.staff.dto;

import com.backend.tradeappbackend.user.dto.UserInfoDTO;
import com.backend.tradeappbackend.userAuthority.UserAuthority;
import com.backend.tradeappbackend.userRole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffInfoDTO extends UserInfoDTO {
    private UUID affiliateCode;

    public StaffInfoDTO(Long userId, String name, String email, String phoneNumber, List<UserRole> userRoles, List<UserAuthority> userAuthorities, UUID affiliateCode) {
        super(userId, name, email, phoneNumber, userRoles, userAuthorities);
        this.affiliateCode = affiliateCode;
    }
}
