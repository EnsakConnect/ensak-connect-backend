package com.ensak.connect.auth.service;

import com.ensak.connect.auth.dto.PasswordResetRequest;
import com.ensak.connect.auth.dto.PasswordResetVerificationRequest;
import com.ensak.connect.auth.model.PasswordReset;
import com.ensak.connect.auth.repository.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;

    public void createPasswordReset(PasswordResetRequest request) {
        passwordResetRepository.save(
                PasswordReset.builder()
                        .email(request.getEmail())
                        .code(generateSixDigitsCode())
                        .build()
        );
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
