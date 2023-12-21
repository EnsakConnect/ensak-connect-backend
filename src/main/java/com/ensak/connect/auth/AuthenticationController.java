package com.ensak.connect.auth;

import com.ensak.connect.auth.dto.*;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        User auth = authenticationService.getAuthenticatedUser();
        return new ResponseEntity<>(UserResponseDTO.map(auth), HttpStatus.ACCEPTED);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateAccountResponse> activate(
            @RequestBody @Valid ActivateAccountRequest request
    ) {
        Boolean res = authenticationService.activate(request);
        return new ResponseEntity<>(
                ActivateAccountResponse.builder()
                        .activationStatus(res)
                        .build()
        , res ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        authenticationService.changePassword(request);
        return ResponseEntity.ok(null);
    }
}
