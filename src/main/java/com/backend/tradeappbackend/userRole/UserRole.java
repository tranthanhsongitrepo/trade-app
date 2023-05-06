package com.backend.tradeappbackend.userRole;

import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.userAuthority.UserAuthority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles", cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_user_with_role",
            joinColumns = @JoinColumn(name = "user_role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_with_role_user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_role_id", "user_with_role_user_id"})
    )
    private List<User> userWithRole;
}
