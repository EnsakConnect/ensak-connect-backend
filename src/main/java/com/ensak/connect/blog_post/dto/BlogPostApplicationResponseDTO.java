package com.ensak.connect.blog_post.dto;

import com.ensak.connect.blog_post.model.BlogPostApplication;
import com.ensak.connect.auth.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder

public class BlogPostApplicationResponseDTO {

    private Integer id;
    private Integer blogPostId;
    private UserResponseDTO applicant;
    private String message;
    private Date createdAt;
    private Date updatedAt;

    public static BlogPostApplicationResponseDTO mapToDTO(BlogPostApplication application){
        return BlogPostApplicationResponseDTO.builder()
                .applicant(UserResponseDTO.map(application.getApplicant()))
                .blogPostId(application.getBlogPost().getId())
                .id(application.getId())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .message(application.getMessage())
                .build();
    }
}
