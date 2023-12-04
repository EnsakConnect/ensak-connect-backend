package com.ensak.connect.question_post.dto.question;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPostRequestDTO {

    @NotBlank(message = "Question should not be blank")
    private String question;
}
