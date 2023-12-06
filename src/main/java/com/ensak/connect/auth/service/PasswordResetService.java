package com.ensak.connect.auth.service;

import com.ensak.connect.auth.dto.PasswordResetRequest;
import com.ensak.connect.auth.dto.PasswordResetVerificationRequest;
import com.ensak.connect.auth.model.PasswordReset;
import com.ensak.connect.auth.repository.PasswordResetRepository;
import com.ensak.connect.email.EmailService;
import com.ensak.connect.email.dto.EmailDTO;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;
    private final UserService userService;
    private final EmailService emailService;

    public void createPasswordReset(PasswordResetRequest request) {
        try {
            User user = userService.getUserByEmail(request.getEmail());
            PasswordReset passwordReset = passwordResetRepository.save(
                    PasswordReset.builder()
                            .email(request.getEmail())
                            .code(generateSixDigitsCode())
                            .build()
            );
            var res = emailService.sendEmail(
                    EmailDTO.builder()
                            .to(request.getEmail())
                            .subject("Ensak Connect - Password reset request")
                            .content("Please confirm your email address using the verification code: " + passwordReset.getCode())
                            .build()
            );
            if(!res) {
                log.warn("Register: Could not send email verification.");
            }
        } catch (Exception ex) {
            log.warn("[createPasswordReset] Something went wrong. ex: " + ex.getMessage());
        }
    }

    public Boolean verifyPasswordReset(PasswordResetVerificationRequest request) {
        return passwordResetRepository
                .findByEmailAndCode(request.getEmail(), request.getCode())
                .isPresent();
    }

    public void deletePasswordReset(String email) {
        passwordResetRepository.deleteByEmail(email);
    }

    private String generateSixDigitsCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
