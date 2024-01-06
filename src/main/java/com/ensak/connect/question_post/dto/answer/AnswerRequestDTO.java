package com.ensak.connect.question_post.dto.answer;


import jakarta.validation.constraints.NotBlank;
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
public class AnswerRequestDTO {
    @NotBlank(message = "Answer should not be blank")
    private String content;

    @Size(max = 10, message = "You can have a maximum of 10 resources")
    private List<Integer> resources = new ArrayList<>();
}
