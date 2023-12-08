package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Profile;
import com.ensak.connect.resource.model.Resource;
import lombok.Builder;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProfileResponseDTO {
    private Integer id;

    private String fullName;

    private String title;

    private Date createdAt;

    private Date updatedAt;

    private String profilePicture;

    public static ProfileResponseDTO mapToDTO(Profile profile){
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .title(profile.getTitle())
                .fullName(profile.getFullName())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
