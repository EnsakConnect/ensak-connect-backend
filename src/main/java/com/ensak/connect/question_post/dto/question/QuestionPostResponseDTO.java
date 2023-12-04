package com.ensak.connect.question_post.dto.question;

import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.user.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class QuestionPostResponseDTO {
    private Integer id;
    private String question;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static QuestionPostResponseDTO map(QuestionPost questionPost) {
        return QuestionPostResponseDTO.builder()
                .id(questionPost.getId())
                .question(questionPost.getQuestion())
                .author(UserResponseDTO.map(questionPost.getAuthor()))
                .createdAt(questionPost.getCreatedAt())
                .updatedAt(questionPost.getUpdatedAt())
                .build();
    }
}
