package com.ensak.connect.auth;

import com.ensak.connect.auth.dto.*;
import com.ensak.connect.auth.model.EmailConfirmation;
import com.ensak.connect.auth.service.EmailConfirmationService;
import com.ensak.connect.config.JwtService;
import com.ensak.connect.email.EmailService;
import com.ensak.connect.email.dto.EmailDTO;
import com.ensak.connect.token.Token;
import com.ensak.connect.token.TokenRepository;
import com.ensak.connect.token.TokenType;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserRepository;
import com.ensak.connect.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final EmailConfirmationService emailConfirmationService;
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

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
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
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
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
        if(! res) {
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
        return (User) authentication.getPrincipal();
    }

    private void sendRegistrationRequest(RegisterRequest request) {
        EmailConfirmation confirmation = emailConfirmationService.createEmailConfirmation(request.getEmail());
        var res = emailService.sendEmail(
                EmailDTO.builder()
                        .to(request.getEmail())
                        .subject("Ensak Connect - Please confirm your email address")
                        .content("Hello "+ request.getFullname() +", Please confirm your email address using the verification code: " + confirmation.getCode())
                        .build()
        );
        if(!res) {
            log.warn("Register: Could not send email verification.");
        }
    }
}
