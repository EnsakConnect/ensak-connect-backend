package com.ensak.connect.auth.dto;

import com.ensak.connect.auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String title;
    private String picture;
    private String email;
    private String role;

    public static UserResponseDTO map(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getProfile().getFullName())
                .title(user.getProfile().getTitle())
                .picture(user.getProfile().getProfilePicture().getFilename())
                .build();
    }
}
