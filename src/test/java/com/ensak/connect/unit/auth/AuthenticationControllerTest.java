package com.ensak.connect.unit.auth;

import com.ensak.connect.auth.AuthenticationController;
import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.dto.AuthenticationRequest;
import com.ensak.connect.auth.dto.AuthenticationResponse;
import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.enumeration.Role;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void testRegister_validRequest_returnsOk() {
        RegisterRequest request = new RegisterRequest();
        request.setFullname("John Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");
        request.setRole(Role.ROLE_USER);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("token");

        when(authenticationService.register(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> actualResponse = authenticationController.register(request);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertThat(actualResponse.getBody()).isEqualTo(response);
    }

    @Test
    public void testLogin_validRequest_returnsOk() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("token");

        when(authenticationService.login(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> actualResponse = authenticationController.login(request);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertThat(actualResponse.getBody()).isEqualTo(response);
    }
}
