package com.ensak.connect.job_post.dto;

import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.auth.dto.UserResponseDTO;
import com.ensak.connect.resource.ResourceDTO;
import com.ensak.connect.resource.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private List<String> resources;
    private Date createdAt;
    private Date updatedAt;

    public static JobPostResponseDTO map(JobPost jobPost) {
        return JobPostResponseDTO.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .companyName(jobPost.getCompanyName())
                .location(jobPost.getLocation())
                .companyType(jobPost.getCompanyType())
                .category(jobPost.getCategory())
                .tags(jobPost.getTags())
                .author(UserResponseDTO.map(jobPost.getAuthor()))
                .resources(ResourceDTO.toString(jobPost.getResources()))
                .createdAt(jobPost.getCreatedAt())
                .updatedAt(jobPost.getUpdatedAt())
                .build();
    }

    public static List<JobPostResponseDTO> map(List<JobPost> jobPosts) {
        if (jobPosts == null) {
            return null;
        }

        List<JobPostResponseDTO> list = new ArrayList<JobPostResponseDTO>(jobPosts.size());
        for (JobPost jobPost : jobPosts) {
            list.add( map(jobPost));
        }

        return list;
    }
}
