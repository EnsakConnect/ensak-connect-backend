package com.ensak.connect.question_post.dto.question;

import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class QuestionPostResponseDTO {
    private Integer id;
    private String question;
    private List<String> tags;
    private ProfileResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static QuestionPostResponseDTO map(QuestionPost questionPost) {
        return QuestionPostResponseDTO.builder()
                .id(questionPost.getId())
                .question(questionPost.getQuestion())
                .tags(questionPost.getTags())
                .author(ProfileResponseDTO.mapToDTO(questionPost.getAuthor()))
                .createdAt(questionPost.getCreatedAt())
                .updatedAt(questionPost.getUpdatedAt())
                .build();
    }
}
