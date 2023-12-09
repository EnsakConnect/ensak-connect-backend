package com.ensak.connect.job_post.dto;

import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.user.User;
import com.ensak.connect.user.dto.UserResponseDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
public class JobApplicationResponseDTO {

    private Integer id;

    private Integer jobPostId;

    private UserResponseDTO applicant;

    private String message;

    private Date createdAt;

    private Date updatedAt;

    public static JobApplicationResponseDTO mapToDTO(JobApplication application){
        return JobApplicationResponseDTO.builder()
                .applicant(UserResponseDTO.map(application.getApplicant()))
                .jobPostId(application.getJobPost().getId())
                .id(application.getId())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .message(application.getMessage())
                .build();
    }
}
