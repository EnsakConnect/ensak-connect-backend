package com.ensak.connect.auth.dto;

import com.ensak.connect.auth.enums.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String role;

    public static UserResponseDTO map(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
