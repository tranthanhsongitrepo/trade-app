package com.backend.tradeappbackend.user;

import com.backend.tradeappbackend.passwordResetRequest.PasswordResetRequest;
import com.backend.tradeappbackend.refreshToken.AuthenticationRefreshToken;
import com.backend.tradeappbackend.stock.Stock;
import com.backend.tradeappbackend.user.dto.UserDTO;
import com.backend.tradeappbackend.userAuthority.UserAuthority;
import com.backend.tradeappbackend.userRole.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(max = 100)
    @NotNull
    private String password;

    @Pattern(regexp = "^[\\p{L} ]+$")
    @NotNull
    private String name;

    @Email
    @Column(unique = true)
    @NotNull
    private String email;

    @Pattern(regexp = "^[0-9]+$")
    @NotNull
    private String phoneNumber;

    private String currentAccessTokenUUID;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userWithRole", cascade = CascadeType.ALL)
    @NotNull
    private List<UserRole> userRoles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersWithAuthority", cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersWithFavoriteStock", cascade = CascadeType.ALL)
    private List<Stock> favoriteStock = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AuthenticationRefreshToken authenticationRefreshToken;

    @ColumnDefault(value = "true")
    private boolean isAccountNonExpired = true;

    @ColumnDefault(value = "true")
    private boolean isAccountNonLocked = true;

    @ColumnDefault(value = "true")
    private boolean isCredentialsNonExpired = true;

    @ColumnDefault(value = "true")
    private boolean isEnabled = true;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PasswordResetRequest passwordResetRequest;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public User(UserDTO userDTO) {
        this.setPassword(userDTO.getPassword());
        this.setEmail(userDTO.getEmail());
        this.setName(userDTO.getName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole userRole : this.getUserRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
            for (UserAuthority userAuthority : userRole.getUserAuthorities()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(userAuthority.getUserAuthorityName()));
            }
        }

        return grantedAuthorities;
    }

    @Override
    public @NotNull String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void mapFromNonNull(UserDTO userDTO) {
        if (userDTO.getPassword() != null)
            this.setPassword(userDTO.getPassword());

        if (userDTO.getEmail() != null)
            this.setEmail(userDTO.getEmail());

        if (userDTO.getName() != null)
            this.setName(userDTO.getName());
    }
}
