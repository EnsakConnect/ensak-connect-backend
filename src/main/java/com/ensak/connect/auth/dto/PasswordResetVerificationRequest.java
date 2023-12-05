package com.ensak.connect.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetVerificationRequest {
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Please provide the code sent by email.")
    @Size(min = 6, max = 6, message = "Activation code must be 6 characters")
    private String code;
}
