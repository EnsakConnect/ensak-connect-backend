package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Profile;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProfileFeedResponseDTO {

    private Integer id;

    private String fullName;

    private String title;

    private Date createdAt;

    private Date updatedAt;

    private String profilePicture;

    private String profileType; //student or professor or laureate

    public static ProfileFeedResponseDTO map(Profile profile, String profileType){
        return ProfileFeedResponseDTO.builder()
                .id(profile.getId())
                .title(profile.getTitle())
                .fullName(profile.getFullName())
                .profilePicture(profile.getProfilePicture())
                .profileType(profileType)
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
