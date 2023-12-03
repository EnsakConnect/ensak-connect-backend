package com.ensak.connect.user.dto;

import com.ensak.connect.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    public static UserResponseDTO map(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
