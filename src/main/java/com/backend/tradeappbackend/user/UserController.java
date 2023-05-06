package com.backend.tradeappbackend.user;

import com.backend.tradeappbackend.jwt.JWTTokenPair;
import com.backend.tradeappbackend.stock.Stock;
import com.backend.tradeappbackend.user.dto.UserDTO;
import com.backend.tradeappbackend.user.dto.UserInfoDTO;
import com.backend.tradeappbackend.user.response.*;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

//    private final RateLimiter authenRateLimiter;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
//        this.authenRateLimiter = authenRateLimiter;
    }

    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody @Validated UserDTO userDTO, BindingResult result) {
        ResponseEntity<?> validationErrorResponses = validateBody(result);
        if (validationErrorResponses != null)
            return validationErrorResponses;

        User user = modelMapper.map(userDTO, User.class);
        user.setProvider(AuthProvider.LOCAL);

        return ResponseEntity.ok(this.userService.addUser(user));

    }

    @Nullable
    private ResponseEntity<?> validateBody(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<ValidationErrorResponse> validationErrorResponses = errors.stream().map(error -> new ValidationErrorResponse(((FieldError) error).getField(), error.getDefaultMessage())).toList();
            return ResponseEntity.badRequest().body(validationErrorResponses);
        }
        return null;
    }

//    @PatchMapping("/forgot-password")
//    public boolean updateUser(Long userId, @RequestBody String newPassword) {
//        this.userService.resetPassword(userId, newPassword);
//        return true;
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Validated UserDTO userDTO, BindingResult result) {
//        boolean acquire = authenRateLimiter.tryAcquire(Duration.ofMillis(1000));
//        if (!acquire) {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        }

        ResponseEntity<?> validationErrorResponses = validateBody(result);
        if (validationErrorResponses != null)
            return validationErrorResponses;

        JWTTokenPair jwtTokenPair = this.userService.authenticateWithUsernameAndPassword(userDTO);

        return ResponseEntity.ok(new AuthenticationResponse("Login successful", jwtTokenPair));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User user = this.userService.findUserById(id);
        UserInfoDTO userInfoDTO = modelMapper.map(user, UserInfoDTO.class);
        return ResponseEntity.ok(userInfoDTO);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginWithOAuth2Token(@RequestParam AuthProvider provider, @RequestParam String token) throws FirebaseAuthException {
        if (provider == AuthProvider.LOCAL) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Local login with JWT token is not supported," +
                    "use /login endpoint", null));
        }
        JWTTokenPair jwtTokenPair = this.userService.authenticateWithOAuth2(provider, token);
        return ResponseEntity.ok(new AuthenticationResponse("Login successful", jwtTokenPair));
    }

    @GetMapping("/logout-success")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        this.userService.logoutUser(request);
        return ResponseEntity.ok(new LogoutResponse("Logout successful"));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshJWTToken(HttpServletRequest httpServletRequest) {
        String accessToken = this.userService.refreshAccessToken(httpServletRequest);

        return ResponseEntity.ok(new TokenRefreshResponse(accessToken));
    }
}
