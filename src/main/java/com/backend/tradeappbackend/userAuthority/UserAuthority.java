package com.backend.tradeappbackend.userAuthority;

import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.userRole.UserRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_user_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthorityId;
    private String userAuthorityName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_user_role_user_authorities",
            joinColumns = @JoinColumn(name = "user_authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_authority_id", "user_role_id"})
    )
    private List<UserRole> userRoles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_users_with_authority",
            joinColumns = @JoinColumn(name = "user_authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersWithAuthority;
}
