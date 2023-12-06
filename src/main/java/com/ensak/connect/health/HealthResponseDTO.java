package com.ensak.connect.health;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HealthResponseDTO {
    private Boolean success;
    private String message;
}
