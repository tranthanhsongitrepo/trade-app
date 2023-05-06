package com.backend.tradeappbackend.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.backend.tradeappbackend.jwt.DecodedAuthorizationDetails;
import com.backend.tradeappbackend.jwt.JWTDecoder;
import com.backend.tradeappbackend.jwt.JWTTokenPair;
import com.backend.tradeappbackend.refreshToken.AuthenticationRefreshToken;
import com.backend.tradeappbackend.refreshToken.AuthenticationRefreshTokenRepository;
import com.backend.tradeappbackend.stock.Stock;
import com.backend.tradeappbackend.stock.StockRepository;
import com.backend.tradeappbackend.user.dto.UserDTO;
import com.backend.tradeappbackend.user.exception.*;
import com.backend.tradeappbackend.userAuthority.AuthenticationRefreshTokenGenerator;
import com.backend.tradeappbackend.userRole.UserRole;
import com.backend.tradeappbackend.userRole.UserRoleRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final StockRepository stockRepository;
    private final FirebaseAuth firebaseAuth;
    private final AuthenticationRefreshTokenRepository authenticationRefreshTokenRepository;
    private final JWTDecoder jwtDecoder;
    @Value("${refreshToken.length}")
    public int TOKEN_LENGTH;
    @Value("${refreshToken.expiration}")
    public Long EXPIRATION;
    @Value("${JWT_SECRET}")
    private String secret;


    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, StockRepository stockRepository, FirebaseAuth firebaseAuth, AuthenticationRefreshTokenRepository authenticationRefreshTokenRepository, JWTDecoder jwtDecoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.stockRepository = stockRepository;
        this.firebaseAuth = firebaseAuth;
        this.authenticationRefreshTokenRepository = authenticationRefreshTokenRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Transactional
    public User addUser(User user) throws EmailExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistsException(user.getEmail());
        }

        UserRole userRole = userRoleRepository.findRoleByRoleName("ROLE_USER");
        userRole.setUserWithRole(List.of(user));
        user.setUserRoles(List.of(userRole));

        if (user.getProvider() == AuthProvider.LOCAL)
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    public User findUserById(Long userId) {
        return this.userRepository.findUserByUserId(userId);
    }

    public User findUserByEmail(String username) throws EmailNotFoundException {
        User user = this.userRepository.findUserByEmail(username);

        if (user == null) {
            throw new EmailNotFoundException(username);
        }

        return user;
    }

    @Transactional
    public JWTTokenPair authenticateWithUsernameAndPassword(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());

        Authentication auth = this.authenticationManager.authenticate(authenticationToken);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        User user = (User) auth.getPrincipal();

        String loggedInDeviceId = UUID.randomUUID().toString();

        String authenticationRefreshTokenString = createRefreshTokenString();
        String accessToken = createAccessToken(user.getEmail(), loggedInDeviceId);

        AuthenticationRefreshToken newAuthenticationRefreshToken = createAuthenticationRefreshToken(user, authenticationRefreshTokenString);

        this.authenticationRefreshTokenRepository.save(newAuthenticationRefreshToken);
        this.userRepository.updateLoggedInDeviceIdByUserId(loggedInDeviceId, user.getUserId());

        return new JWTTokenPair(accessToken, authenticationRefreshTokenString);
    }

    public JWTTokenPair authenticateWithOAuth2(AuthProvider provider, String oAuthAccessToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = this.firebaseAuth.verifyIdToken(oAuthAccessToken);

        String tokenEmail = decodedToken.getEmail();
        User user = null;

        try {
            user = this.findUserByEmail(tokenEmail);
        } catch (EmailNotFoundException exception) {
            // Use decodedToken to obtain user info and create a custom token for the user
            user = new User();
            user.setName(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setProviderId(decodedToken.getUid());
            user.setProvider(provider);

            this.addUser(user);
        }

        String loggedInDeviceId = UUID.randomUUID().toString();

        String accessToken = createAccessToken(user.getEmail(), loggedInDeviceId);
        String authenticationRefreshToken = createRefreshTokenString();

        AuthenticationRefreshToken refreshToken = createAuthenticationRefreshToken(user, authenticationRefreshToken);

        authenticationRefreshTokenRepository.save(refreshToken);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        return new JWTTokenPair(accessToken, authenticationRefreshToken);
    }

    public AuthenticationRefreshToken createAuthenticationRefreshToken(User user, String authenticationRefreshToken) {
        AuthenticationRefreshToken userAuthenticationRefreshToken = user.getAuthenticationRefreshToken();

        String hashedAuthenticationRefreshToken = DigestUtils.sha256Hex(authenticationRefreshToken);

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime expiryDate = createdDate.plusSeconds(this.EXPIRATION);

        AuthenticationRefreshToken newUserAuthenticationRefreshToken = new AuthenticationRefreshToken(hashedAuthenticationRefreshToken, createdDate, expiryDate, user);
        if (userAuthenticationRefreshToken != null)
            newUserAuthenticationRefreshToken.setAuthRefreshTokenId(userAuthenticationRefreshToken.getAuthRefreshTokenId());

        return newUserAuthenticationRefreshToken;
    }

    public String createAccessToken(String userEmail, String loggedInDeviceId) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret.getBytes());

        return JWT.create().withSubject(userEmail)
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .withIssuer("local")
                .withClaim("loggedInDeviceId", loggedInDeviceId)
//                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                    .withClaim("issuer", "local")
                .sign(algorithm);
    }

    public String createRefreshTokenString() {
//        Algorithm algorithm = Algorithm.HMAC256(this.secret.getBytes());
        AuthenticationRefreshTokenGenerator authenticationRefreshTokenGenerator = new AuthenticationRefreshTokenGenerator();

        return authenticationRefreshTokenGenerator.generateRefreshToken();
    }

    public String refreshAccessToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AuthorizationHeaderException();
        }

        String token = authorizationHeader.substring("Bearer ".length());
        String hashedToken = DigestUtils.sha256Hex(token);

        AuthenticationRefreshToken authenticationRefreshToken = this.findAuthenticationRefreshTokenByHashedRefreshTokenString(hashedToken);

        if (authenticationRefreshToken == null) {
            throw new AuthenticationRefreshTokenNotExistsException();
        }

        if (authenticationRefreshToken.isExpired()) {
            throw new AuthenticationRefreshTokenExpiredException();
        } else {
            User user = authenticationRefreshToken.getUser();
            return this.createAccessToken(user.getEmail(), user.getCurrentAccessTokenUUID());
        }
    }

    private AuthenticationRefreshToken findAuthenticationRefreshTokenByHashedRefreshTokenString(String hashedToken) {
        return this.authenticationRefreshTokenRepository.findAuthenticationRefreshTokenByHashedRefreshTokenString(hashedToken);
    }

    @Transactional
    public void logoutUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedAuthorizationDetails decodedJWT = this.jwtDecoder.decode(token);

                this.authenticationRefreshTokenRepository.revokeRefreshTokenByUserEmail(decodedJWT.getEmail());
                this.userRepository.updateLoggedInDeviceIdByUserEmail(null, decodedJWT.getEmail());
            } catch (TokenExpiredException e) {
                throw new AuthenticationRefreshTokenExpiredException();
            }
        }

        SecurityContextHolder.clearContext();
    }
}
