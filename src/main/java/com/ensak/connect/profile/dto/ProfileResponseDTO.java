package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Profile;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
public class ProfileResponseDTO {
    private Integer id;

    private String fullName;

    private String titre;

    private String phone;

    private String city;

    private String address;

    private Date createdAt;

    private Date updatedAt;

    //gotta need to add the ids of all the resourceFile (Profile pic, Banner, CV)

    public static ProfileResponseDTO mapToDTO(Profile profile){
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .titre(profile.getTitre())
                .fullName(profile.getFullName())
                .phone(profile.getPhone())
                .city(profile.getCity())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .address(profile.getAddress()).build();
    }
}
