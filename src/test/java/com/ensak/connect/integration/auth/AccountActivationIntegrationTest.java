package com.ensak.connect.integration.auth;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.dto.*;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.EmailConfirmation;
import com.ensak.connect.auth.model.PasswordReset;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.EmailConfirmationRepository;
import com.ensak.connect.auth.repository.PasswordResetRepository;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.integration.FakeRequest;
import com.ensak.connect.profile.model.util.ProfileType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountActivationIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailConfirmationRepository emailConfirmationRepository;


    @Test
    public void itShouldCreateNonActivatedUserOnRegisterAndActivateOnValidCode() throws Exception {
        authenticationService.register(
                RegisterRequest.builder()
                        .fullname("test activation")
                        .email("test.activation@ensakconnect.com")
                        .password("password")
                        .role(ProfileType.LAUREATE.toString())
                        .build()
        );

        User user = userRepository.findByEmail("test.activation@ensakconnect.com")
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        assertNull(user.getActivatedAt());
        EmailConfirmation emailConfirmation = emailConfirmationRepository.findOneByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException("Cannot find email confirmation"));
        this.authenticateAs(user);

        ActivateAccountRequest request = ActivateAccountRequest.builder()
                .code(emailConfirmation.getCode())
                .email(user.getEmail())
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);

        api.perform(
                post("/api/v1/auth/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isAccepted());

        User userAfter = userRepository.findByEmail("test.activation@ensakconnect.com")
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        assertNotNull(userAfter.getActivatedAt());
    }
}
