package com.ensak.connect.qna_post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QNAPostRequestDTO {

    @NotBlank(message = "Question should not be blank")
    private String question;
}
