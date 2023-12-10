package com.ensak.connect.question_post.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPostRequestDTO {

    @NotBlank(message = "Question should not be blank")
    private String question;

    @Size(max = 10, message = "You can have a maximum of 10 tags")
    List<String> tags = new ArrayList<>();
}
