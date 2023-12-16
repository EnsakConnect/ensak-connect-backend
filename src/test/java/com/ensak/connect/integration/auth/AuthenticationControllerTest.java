package com.ensak.connect.integration.auth;

import com.ensak.connect.auth.dto.AuthenticationRequest;
import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest extends AuthenticatedBaseIntegrationTest {

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserRepository userRepository;
    @Test
    @Transactional
    public void loginWithValidCredentials() throws Exception {
        /*User user= User.builder()
                .email("hassanayoub.benasser@uit.ac.ma")
                .password("hassan123")
                .role(Role.ROLE_USER)
                .profileType("STUDENT")
                .build();
        userRepository.save(user);*/
        this.createDummyStudent();

        AuthenticationRequest validRequest = new AuthenticationRequest();
        validRequest.setEmail("user.user@email.com");
        validRequest.setPassword("password");

        String requestJson = objectMapper.writeValueAsString(validRequest);

        String response = api.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void loginWithInvalidCredentials() throws Exception {
        AuthenticationRequest invalidRequest = new AuthenticationRequest();
        invalidRequest.setEmail("invalid@example.com");
        invalidRequest.setPassword("invalidPassword");

        String requestJson = objectMapper.writeValueAsString(invalidRequest);

        api.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }
}