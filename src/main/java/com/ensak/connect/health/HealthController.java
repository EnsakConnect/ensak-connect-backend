package com.ensak.connect.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<HealthResponseDTO> get() {
        return new ResponseEntity<>(
                HealthResponseDTO.builder()
                        .success(true)
                        .message("Server up and running")
                        .build(),
                HttpStatus.OK
        );
    }
}
