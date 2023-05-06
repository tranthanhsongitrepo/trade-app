package com.backend.tradeappbackend.passwordResetRequest;

import com.backend.tradeappbackend.passwordResetRequest.dto.PasswordResetRequestCreateDTO;
import com.backend.tradeappbackend.passwordResetRequest.dto.PasswordResetRequestProcessDTO;
import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.user.UserRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.tradeappbackend.passwordResetRequest.exception.PasswordResetServiceException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetRequestRepository passwordResetRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordEncoder uuidEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public PasswordResetService(UserRepository userRepository, PasswordResetRequestRepository passwordResetRequestRepository, PasswordEncoder passwordEncoder, PasswordEncoder uuidEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordResetRequestRepository = passwordResetRequestRepository;
        this.passwordEncoder = passwordEncoder;
        this.uuidEncoder = uuidEncoder;
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String email, String uuid, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("http://localhost:8080/api/v1/auth/password-reset/" + uuid + "&" + id);
        mailSender.send(message);
    }

    public void createPasswordResetRequest(@NotNull PasswordResetRequestCreateDTO passwordResetRequestCreateDTO) {
        User user = userRepository.findUserByEmail(passwordResetRequestCreateDTO.getEmail());

        if (user == null) {
            return;
        }

        PasswordResetRequest passwordResetRequest = user.getPasswordResetRequest();
        if (passwordResetRequest == null) {
            passwordResetRequest = new PasswordResetRequest();
        }

        passwordResetRequest.setUser(user);

        // This token should be 128 bit of entropy
        PasswordResetTokenGenerator keyGenerator = new PasswordResetTokenGenerator();
        String token = keyGenerator.generateToken();
        passwordResetRequest.setToken(uuidEncoder.encode(token));
        passwordResetRequest.setCreatedDate(LocalDateTime.now());
        passwordResetRequest = passwordResetRequestRepository.save(passwordResetRequest);
        this.sendPasswordResetEmail(passwordResetRequest.getUser().getEmail(), token, passwordResetRequest.getId());
    }

    @Transactional
    public void processPasswordResetRequest(String token, Long tokenId, PasswordResetRequestProcessDTO passwordResetRequestProcessDTO) throws PasswordResetServiceException {
        Optional<PasswordResetRequest> passwordResetRequest = passwordResetRequestRepository.findById(tokenId);
        if (passwordResetRequest.isEmpty()) {
            throw new PasswordResetServiceException("Token not found");
        } else {
            if (passwordResetRequest.get().isTokenExpired()) {
//                passwordResetRequestRepository.delete(passwordResetRequest.get());
                throw new PasswordResetServiceException("Token expired");
            }
        }

        if (this.uuidEncoder.matches(token, passwordResetRequest.get().getToken())) {
            User user = passwordResetRequest.get().getUser();
            user.setPassword(passwordEncoder.encode(passwordResetRequestProcessDTO.getNewPassword()));
            userRepository.save(user);
            passwordResetRequestRepository.deleteById(passwordResetRequest.get().getId());

            // TODO: Destroy all active sessions with this user
            // TODO: Destroy all remember me tokens with this user
            // TODO: Send an email to the user to notify the changes
        } else {
            throw new PasswordResetServiceException("Token is invalid");
        }
    }
}
