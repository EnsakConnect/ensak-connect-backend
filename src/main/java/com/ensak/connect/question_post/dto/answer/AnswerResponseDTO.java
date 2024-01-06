package com.ensak.connect.question_post.dto.answer;

import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.resource.ResourceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AnswerResponseDTO {
    private Integer id;
    private String content;
    private ProfileResponseDTO author;
    private List<String> resources;
    private Date createdAt;
    private Date updatedAt;

    public static AnswerResponseDTO map(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .author(ProfileResponseDTO.mapToDTO(answer.getAuthor()))
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .resources(
                        ResourceDTO.toString(answer.getResources())
                )
                .build();
    }
}
