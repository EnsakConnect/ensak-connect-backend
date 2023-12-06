package com.ensak.connect.auth.service;

import com.ensak.connect.auth.model.EmailConfirmation;
import com.ensak.connect.auth.repository.EmailConfirmationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {

    private final EmailConfirmationRepository emailConfirmationRepository;

    public EmailConfirmation createEmailConfirmation(String email) {
        return emailConfirmationRepository.save(
                EmailConfirmation.builder()
                        .email(email)
                        .code(generateSixDigitsCode())
                        .build()
        );
    }

    public Boolean verify(String email, String code) {
        return emailConfirmationRepository.findByEmailAndCode(email, code).isPresent();
    }

    @Transactional
    public void deleteEmailConfirmation(String email) {
        emailConfirmationRepository.deleteByEmail(email);
    }

    private String generateSixDigitsCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
