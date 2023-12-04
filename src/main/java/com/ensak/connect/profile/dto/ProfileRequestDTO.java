package com.ensak.connect.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ProfileRequestDTO {
    private String titre;

    private String fullName;

    private String phone;

    private String city;

    private String address;

    //gotta need to add the ids of all the resourceFile (Profile pic, Banner, CV)
}
