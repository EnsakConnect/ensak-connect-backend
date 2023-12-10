package com.ensak.connect.auth.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.dto.PasswordResetRequest;
import com.ensak.connect.auth.dto.PasswordResetVerificationRequest;
import com.ensak.connect.auth.dto.PasswordResetVerificationResponse;
import com.ensak.connect.auth.service.PasswordResetService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Void> createPasswordReset(
            @RequestBody @Valid PasswordResetRequest request
    ) {
        passwordResetService.createPasswordReset(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    @Transactional
    public ResponseEntity<PasswordResetVerificationResponse> verify(
            @RequestBody @Valid PasswordResetVerificationRequest request
    ) {
        Boolean res = passwordResetService.verifyPasswordReset(request);
        if(!res) {
            return new ResponseEntity<>(
                    PasswordResetVerificationResponse.builder()
                            .status(false)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        passwordResetService.deletePasswordReset(request.getEmail());

        String token = authenticationService.generateTokenForEmail(request.getEmail());
        return new ResponseEntity<>(
                PasswordResetVerificationResponse.builder()
                        .status(true)
                        .token(token)
                        .build()
                , HttpStatus.ACCEPTED);
    }
}
