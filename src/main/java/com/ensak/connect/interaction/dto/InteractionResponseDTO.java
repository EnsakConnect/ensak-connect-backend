package com.ensak.connect.interaction.dto;

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
public class InteractionResponseDTO {

    private String message;


}
