package com.ensak.connect.blog_post.dto;

import com.ensak.connect.blog_post.model.CommentBlogPost;
import com.ensak.connect.auth.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor

public class CommentBlogPostResponseDTO {

    private Integer id;
    private String content;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static CommentBlogPostResponseDTO map(CommentBlogPost commentBlogPost){
        return CommentBlogPostResponseDTO.builder()
                .id(commentBlogPost.getId())
                .content(commentBlogPost.getContent())
                .author(UserResponseDTO.map(commentBlogPost.getAuthor()))
                .createdAt(commentBlogPost.getCreatedAt())
                .updatedAt(commentBlogPost.getUpdatedAt())
                .build();
    }


}
