package com.ensak.connect.auth;

import com.ensak.connect.auth.dto.ActivateAccountRequest;
import com.ensak.connect.auth.dto.AuthenticationRequest;
import com.ensak.connect.auth.dto.AuthenticationResponse;
import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.email_confirmation.EmailConfirmation;
import com.ensak.connect.auth.email_confirmation.EmailConfirmationService;
import com.ensak.connect.config.JwtService;
import com.ensak.connect.email.EmailService;
import com.ensak.connect.email.dto.EmailDTO;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final EmailConfirmationService emailConfirmationService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = userService.createUser(request);
        this.sendRegistrationRequest(request);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userService.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
