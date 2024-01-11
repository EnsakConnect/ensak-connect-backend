package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.profile.model.util.ProfileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileRequestDTO {

    @NotBlank(message = "title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @NotBlank(message = "fullname is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "city is required")
    @Size(min = 3, max = 50, message = "City must be between 3 and 50 characters")
    private String city;

    @NotBlank(message = "address is required")
    @Size(min = 3, max = 200, message = "Address must be between 3 and 200 characters")
    private String address;

    @NotBlank(message = "profileType is required")
    @Pattern(regexp = "STUDENT|LAUREATE|PROFESSOR", message = "profileType must be STUDENT, LAUREATE or PROFESSOR")
    private String profileType;

    //optional
    private String description;
}
