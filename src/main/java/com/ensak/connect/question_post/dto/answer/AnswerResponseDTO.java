package com.ensak.connect.question_post.dto.answer;

import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.auth.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class AnswerResponseDTO {
    private Integer id;
    private String content;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static AnswerResponseDTO map(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .author(UserResponseDTO.map(answer.getAuthor()))
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}
