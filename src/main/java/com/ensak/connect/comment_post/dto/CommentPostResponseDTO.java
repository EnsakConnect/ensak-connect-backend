package com.ensak.connect.comment_post.dto;

import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.user.dto.UserResponseDTO;
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

    public static CommentPostResponseDTO map(Answer answer) {
        return CommentPostResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .author(UserResponseDTO.map(answer.getAuthor()))
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}
