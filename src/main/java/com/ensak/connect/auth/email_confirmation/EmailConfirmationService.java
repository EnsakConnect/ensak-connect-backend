package com.ensak.connect.auth.email_confirmation;

import lombok.AllArgsConstructor;
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

    private String generateSixDigitsCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
