package com.ensak.connect.auth;

import com.ensak.connect.auth.dto.*;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.EmailConfirmation;
import com.ensak.connect.auth.service.EmailConfirmationService;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.config.security.JwtService;
import com.ensak.connect.config.security.listener.LoginAttemptService;
import com.ensak.connect.util.email.EmailService;
import com.ensak.connect.util.email.dto.EmailDTO;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.auth.model.Token;
import com.ensak.connect.auth.repository.TokenRepository;
import com.ensak.connect.auth.enums.TokenType;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final EmailConfirmationService emailConfirmationService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            validateLoginAttempt(user.get());
            userRepository.save(user.get());
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(), user.get().getPassword(), user.get().isEnabled(), true, true, user.get().getIsNotLocked(),
                    new ArrayList<>());
        }

    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = userService.createUser(request);
        this.sendRegistrationRequest(request);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse login(String origin, AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(
                        () -> new BadCredentialsException("Email or password incorrect")
                );
        if (user.getRole().equals(Role.ROLE_USER) && origin != null && origin.contains("localhost:4200")){
            throw new BadCredentialsException("Email or password incorrect");
        }
        loadUserByUsername(request.getEmail().toLowerCase());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase(),
                        request.getPassword()
                )
        );

        String jwtToken = this.generateTokenForEmail(request.getEmail());
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail.toLowerCase())
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String generateTokenForEmail(String email) {
        var user = userService.getUserByEmail(email);
        return jwtService.generateToken(user);
    }

    public Boolean activate(ActivateAccountRequest request) {
        Boolean res = emailConfirmationService.verify(request.getEmail(), request.getCode());
        if (!res) {
            return false;
        }
        emailConfirmationService.deleteEmailConfirmation(request.getEmail());
        userService.activateUser(request.getEmail());
        return true;
    }

    public void changePassword(ChangePasswordRequest request) {
        User auth = getAuthenticatedUser();
        userService.updatePassword(auth.getId(), request.getPassword());
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userRepository.findById(user.getId()).get();
    }

    private void sendRegistrationRequest(RegisterRequest request) {
        EmailConfirmation confirmation = emailConfirmationService.createEmailConfirmation(request.getEmail());
        var res = emailService.sendEmail(
                EmailDTO.builder()
                        .to(request.getEmail())
                        .subject("Ensak Connect - Please confirm your email address")
                        .content("Hello " + request.getFullname() + ", Please confirm your email address using the verification code: " + confirmation.getCode())
                        .build()
        );
        if (!res) {
            log.warn("Register: Could not send email verification.");
        }
    }

    private void validateLoginAttempt(User user) {
        if (user.getIsNotLocked()) {
            user.setIsNotLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
