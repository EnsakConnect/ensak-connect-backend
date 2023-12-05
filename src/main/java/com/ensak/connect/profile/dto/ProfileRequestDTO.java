package com.ensak.connect.profile.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
public class ProfileRequestDTO {

    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Size(min = 3, max = 50, message = "City must be between 3 and 50 characters")
    private String city;

    @Size(min = 3, max = 200, message = "Address must be between 3 and 200 characters")
    private String address;
    //gotta need to add the ids of all the resourceFile (Profile pic, Banner, CV)
}
