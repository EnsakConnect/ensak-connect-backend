package com.ensak.connect.unit.auth;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.dto.AuthenticationRequest;
import com.ensak.connect.auth.dto.AuthenticationResponse;
import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.config.JwtService;
import com.ensak.connect.email.EmailService;
import com.ensak.connect.enumeration.Role;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserRepository;
import com.ensak.connect.user.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFullname("John Dao");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");
        request.setRole(Role.ROLE_STUDENT);

        User user = User.builder()
                .email("johndoe@example.com")
                .password("hashedPassword")
                .role(Role.ROLE_STUDENT)
                .build();

        when(userService.createUser(request)).thenReturn(user);

        authenticationService.register(request);

        //verify(userRepository, Mockito.times(1)).save(user);
        verify(jwtService, Mockito.times(1)).generateToken(user);
    }

    @Test
    public void testLogin_validCredentials_returnsAuthToken() {

        User user = new User();
        user.setActivatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("password");
        user.setRole(Role.ROLE_STUDENT);
        Optional<User> ofResult = Optional.of(user);
        //when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userService.getUserByEmail(Mockito.<String>any())).thenReturn(user);
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        AuthenticationResponse actualLoginResult = authenticationService
                .login(new AuthenticationRequest("jane.doe@example.org", "password"));
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(userService).getUserByEmail(Mockito.<String>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("ABC123", actualLoginResult.getToken());

    }
}
