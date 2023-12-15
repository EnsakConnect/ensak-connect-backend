package com.ensak.connect.integration.auth;

import com.ensak.connect.auth.dto.PasswordResetRequest;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.PasswordReset;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.PasswordResetRepository;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.integration.FakeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PasswordResetIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void itShouldCreatePasswordResetQueryWhenRequestIsValid() throws Exception {
        User user = userRepository.save(
                User.builder()
                        .password("random")
                        .role(Role.ROLE_USER)
                        .email("demo@testensakconnect.com")
                        .build()
        );
        String url = "/api/v1/auth/password-reset";
        PasswordResetRequest request = PasswordResetRequest.builder()
                .email(user.getEmail())
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);
        api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isCreated());

        Optional<PasswordReset> passwordResetO = passwordResetRepository.findByEmail(user.getEmail());
        assertTrue(passwordResetO.isPresent());
        PasswordReset passwordReset = passwordResetO.get();
        assertEquals(user.getEmail(), passwordReset.getEmail());
    }

    @Test
    public void itShouldNotCreatePasswordResetQueryWhenRequestIsNotValid() throws Exception {
        String url = "/api/v1/auth/password-reset";
        String requestJSON = objectMapper.writeValueAsString(new FakeRequest("random value"));
        System.out.println(requestJSON);
        api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldNotCreatePasswordResetQueryWhenEmailDoesNotExistAndReturnCreatedStatus() throws Exception {
        /**
         * Should not create the request since user does not exist
         * But should not return a failed status since
         * that could be exploited to get our
         * users emails.
         */
        String url = "/api/v1/auth/password-reset";
        String randomEmail = "notexists@testensakconnect.com";
        PasswordResetRequest request = PasswordResetRequest.builder()
                .email(randomEmail)
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);
        api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isCreated());

        Optional<PasswordReset> passwordResetO = passwordResetRepository.findByEmail(randomEmail);
        assertTrue(passwordResetO.isEmpty());
    }
}
