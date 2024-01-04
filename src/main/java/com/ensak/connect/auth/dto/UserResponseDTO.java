package com.ensak.connect.auth.dto;

import com.ensak.connect.auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

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
        var userBuilder =  UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name());
        if(user.getProfile() != null){
            userBuilder.name(user.getProfile().getFullName())
                    .title(user.getProfile().getTitle())
                    .picture(
                            user.getProfile() == null || user.getProfile().getProfilePicture() == null ?
                                    null :
                                    user.getProfile().getProfilePicture().getFilename()
                    );
        }

        return userBuilder.build();
    }
}
