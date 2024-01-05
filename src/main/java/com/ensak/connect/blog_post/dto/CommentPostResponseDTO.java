package com.ensak.connect.blog_post.dto;

import com.ensak.connect.blog_post.model.CommentPost;
import com.ensak.connect.auth.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CommentPostResponseDTO {
    private Integer id;
    private String content;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static CommentPostResponseDTO map(CommentPost commentPost) {
        return CommentPostResponseDTO.builder()
                .id(commentPost.getId())
                .content(commentPost.getContent())
                .author(UserResponseDTO.map(commentPost.getAuthor()))
                .createdAt(commentPost.getCreatedAt())
                .updatedAt(commentPost.getUpdatedAt())
                .build();
    }
}
