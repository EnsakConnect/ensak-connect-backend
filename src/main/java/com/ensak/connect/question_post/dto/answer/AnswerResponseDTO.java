package com.ensak.connect.question_post.dto.answer;

import com.ensak.connect.interaction.model.Interaction;
import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.resource.ResourceDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
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
    private Boolean isUp;
    private Integer interactionsCount;
    private Date createdAt;
    private Date updatedAt;

    public static AnswerResponseDTO map(Answer answer, Integer authorId) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .author(ProfileResponseDTO.mapToDTO(answer.getAuthor()))
                .interactionsCount(answer.getInteractionsCount())
                .isUp(isUpChecker(answer.getInteractions(), authorId))
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .resources(
                        answer.getResources() != null ? ResourceDTO.toString(answer.getResources()) : null
                )
                .build();
    }

    public static List<AnswerResponseDTO> map(List<Answer> answers, Integer authorId) {
        if (answers == null) {
            return null;
        }

        List<AnswerResponseDTO> list = new ArrayList<AnswerResponseDTO>(answers.size());
        for (Answer answer : answers) {
            list.add( map(answer, authorId));
        }

        return list;
    }

    private static Boolean isUpChecker(List<Interaction> interactions, Integer id) {
        if (interactions == null){
            return null;
        }
        for (Interaction interaction : interactions){
            if (interaction.getAuthor().getId().equals(id)) {
                return interaction.getIsUp();
            }
        }
        return null;
    }
}


