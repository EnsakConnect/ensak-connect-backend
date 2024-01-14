package com.ensak.connect.job_post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostRequestDTO {

    @NotBlank(message = "Job title should not be blank")
    private String title;

    @NotBlank(message = "Description should not be blank")
    private String description;

    @NotBlank(message = "Company name should not be blank")
    private String companyName;

    @NotBlank(message = "Location should not be blank")
    private String location;

    @NotBlank(message = "Company type should not be blank")
    private String companyType;

    //optional
    private String companyLogo;

    @NotBlank(message = "Category should not be blank")
    private String category;

    @Size(max = 10, message = "You can have a maximum of 10 tags")
    private List<String> tags = new ArrayList<>();

    @Size(max = 10, message = "You can have a maximum of 10 resources")
    private List<Integer> resources = new ArrayList<>();
}
