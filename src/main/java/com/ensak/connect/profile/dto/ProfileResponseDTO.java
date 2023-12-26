package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.profile.model.util.ProfileType;
import com.ensak.connect.resource.model.Resource;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
public class ProfileResponseDTO {
    private Integer id;

    private String fullName;

    private String title;

    private Date createdAt;

    private Date updatedAt;

    private String profilePicture;

    private ProfileType profileType;

    public static ProfileResponseDTO mapToDTO(Profile profile){
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .title(profile.getTitle())
                .fullName(profile.getFullName())
                .profilePicture(
                        (profile.getProfilePicture()!=null)?profile.getProfilePicture().getFilename():null
                )
                .profileType(profile.getProfileType())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
