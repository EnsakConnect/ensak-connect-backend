package com.ensak.connect.job_post.dto;

import com.ensak.connect.job_post.JobPost;
import com.ensak.connect.user.dto.UserResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private String companyType;
    private String category;
    private List<String> tags;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static JobPostResponseDTO map(JobPost jobPost) {
        return JobPostResponseDTO.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getTitle())
                .companyName(jobPost.getCompanyName())
                .location(jobPost.getLocation())
                .companyType(jobPost.getCompanyType())
                .category(jobPost.getCategory())
                .tags(jobPost.getTags())
                .author(UserResponseDTO.map(jobPost.getAuthor()))
                .createdAt(jobPost.getCreatedAt())
                .updatedAt(jobPost.getUpdatedAt())
                .build();
    }
}
