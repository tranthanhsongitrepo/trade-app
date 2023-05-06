package com.backend.tradeappbackend.passwordResetRequest;

import com.backend.tradeappbackend.passwordResetRequest.dto.PasswordResetRequestCreateDTO;
import com.backend.tradeappbackend.passwordResetRequest.dto.PasswordResetRequestProcessDTO;
import com.backend.tradeappbackend.passwordResetRequest.exception.PasswordResetServiceException;
import com.backend.tradeappbackend.passwordResetRequest.response.PasswordResetRequestCreatedResponse;
import com.backend.tradeappbackend.passwordResetRequest.response.PasswordResetRequestProcessedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth/password-reset")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createPasswordResetRequest(@RequestBody @Validated PasswordResetRequestCreateDTO passwordResetRequestCreateDTO) {
        passwordResetService.createPasswordResetRequest(passwordResetRequestCreateDTO);
        return ResponseEntity.ok(new PasswordResetRequestCreatedResponse());
    }

    @PostMapping("/{token}&{tokenId}")
    public ResponseEntity<?> processPasswordResetRequest(@PathVariable(value = "token") @NotEmpty String token,
                                                         @PathVariable(value = "tokenId") @NotEmpty Long tokenId,
                                                         @RequestBody @Validated PasswordResetRequestProcessDTO passwordResetRequestProcessDTO) throws PasswordResetServiceException {
        passwordResetService.processPasswordResetRequest(token, tokenId, passwordResetRequestProcessDTO);
        return ResponseEntity.ok(new PasswordResetRequestProcessedResponse());
    }
}
