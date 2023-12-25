package com.ensak.connect.integration.auth;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.dto.AuthenticationRequest;
import com.ensak.connect.auth.dto.ChangePasswordRequest;
import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.model.util.ProfileType;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private AuthenticationService authenticationService;

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
        var user =this.createDummyStudent();
        AuthenticationRequest validRequest = new AuthenticationRequest();
        validRequest.setEmail("student.user@email.com");
        validRequest.setPassword("password");

        String requestJson = objectMapper.writeValueAsString(validRequest);

        String response = api.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Transactional
    public void loginWithInvalidCredentials() throws Exception {
        var user =this.createDummyStudent();
        AuthenticationRequest invalidRequest = new AuthenticationRequest();
        invalidRequest.setEmail("student.user@email.com");
        invalidRequest.setPassword("password123");

        String requestJson = objectMapper.writeValueAsString(invalidRequest);

        String response = api.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Transactional
    public void isShouldEnableUserToRegister() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email("test.register@ensakconnect.com")
                .fullname("Test Register")
                .role(ProfileType.LAUREATE.toString())
                .password("password")
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);

        api.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isOk());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        assertEquals(request.getEmail(), user.getEmail());
        assertNull(user.getActivatedAt());
    }

    @Test
    @Transactional
    public void isShouldNotEnableUserToRegisterIfEmailExists() throws Exception {
        authenticationService.register(
                RegisterRequest.builder()
                        .fullname("test email exists")
                        .email("test.email.exits@ensakconnect.com")
                        .password("password")
                        .role(ProfileType.LAUREATE.toString())
                        .build()
        );
        RegisterRequest request = RegisterRequest.builder()
                .email("test.email.exits@ensakconnect.com")
                .fullname("Test Register")
                .role(ProfileType.LAUREATE.toString())
                .password("password")
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);

        api.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void isShouldChangePassword() throws Exception {
        User user = this.authenticateAsUser();
        String request = objectMapper.writeValueAsString(
                ChangePasswordRequest.builder()
                        .password("newpassword")
                        .build()
        );

        api.perform(
                post("/api/v1/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(status().isOk());

        String loginRequest = objectMapper.writeValueAsString(
                AuthenticationRequest.builder()
                        .email(user.getEmail())
                        .password("newpassword")
                        .build()
        );
        api.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest)
        ).andExpect(status().isOk());
    }
}