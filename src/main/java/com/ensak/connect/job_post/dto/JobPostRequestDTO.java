package com.ensak.connect.job_post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "Category should not be blank")
    private String category;

    @NotBlank(message = "Tags should not be blank")
    private List<String> tags;
}
