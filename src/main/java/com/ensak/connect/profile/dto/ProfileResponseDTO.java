package com.ensak.connect.profile.dto;

import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.profile.model.util.ProfileType;
import com.ensak.connect.resource.model.Resource;
import lombok.Builder;
import lombok.Data;


import java.util.ArrayList;
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

    public static List<ProfileResponseDTO> map(List<Profile> profiles) {
        if (profiles == null) {
            return null;
        }

        List<ProfileResponseDTO> list = new ArrayList<ProfileResponseDTO>(profiles.size());
        for (Profile profile : profiles) {
            list.add( mapToDTO(profile));
        }

        return list;
    }
}
