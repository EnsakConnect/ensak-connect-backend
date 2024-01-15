package com.ensak.connect.job_post.dto;

import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.resource.ResourceDTO;
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
    private Boolean isLiked;
    private Integer applicantsCount;
    private Integer likesCount;
    private List<String> tags;
    private ProfileResponseDTO author;
    private List<String> resources;
    private Date createdAt;
    private Date updatedAt;

    public static JobPostResponseDTO map(JobPost jobPost, Integer authorId) {
        return JobPostResponseDTO.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .companyName(jobPost.getCompanyName())
                .location(jobPost.getLocation())
                .isLiked(jobPost.getLikes() != null && jobPost.getLikes().contains(authorId))
                .likesCount(jobPost.getLikes() != null ? jobPost.getLikes().size() : 0)
                .applicantsCount(jobPost.getJobApplications() != null ? jobPost.getJobApplications().size() : 0)
                .companyType(jobPost.getCompanyType())
                .category(jobPost.getCategory())
                .tags(jobPost.getTags())
                .author(ProfileResponseDTO.mapToDTO(jobPost.getAuthor()))
                .resources(ResourceDTO.toString(jobPost.getResources()))
                .createdAt(jobPost.getCreatedAt())
                .updatedAt(jobPost.getUpdatedAt())
                .build();
    }

    public static List<JobPostResponseDTO> map(List<JobPost> jobPosts, Integer authorId) {
        if (jobPosts == null) {
            return null;
        }

        List<JobPostResponseDTO> list = new ArrayList<JobPostResponseDTO>(jobPosts.size());
        for (JobPost jobPost : jobPosts) {
            list.add( map(jobPost, authorId));
        }

        return list;
    }
}
