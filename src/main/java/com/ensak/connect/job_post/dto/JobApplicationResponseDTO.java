package com.ensak.connect.job_post.dto;

import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JobApplicationResponseDTO {

    private Integer id;

    private Integer jobPostId;

    private ProfileResponseDTO applicant;

    private String message;

    private Date createdAt;

    private Date updatedAt;

    public static JobApplicationResponseDTO mapToDTO(JobApplication application){
        return JobApplicationResponseDTO.builder()
                .applicant(ProfileResponseDTO.mapToDTO(application.getApplicant()))
                .jobPostId(application.getJobPost().getId())
                .id(application.getId())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .message(application.getMessage())
                .build();
    }
}
