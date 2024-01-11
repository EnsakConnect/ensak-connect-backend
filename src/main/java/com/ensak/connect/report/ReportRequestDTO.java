package com.ensak.connect.report;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {

    @NotBlank(message = "postType is required")
    @Pattern(regexp = "JOB|BLOG|QUESTION|ANSWER|COMMENT", message = "postType must be JOB, BLOG, QUESTION, ANSWER or COMMENT")
    private String postType;

    @NotNull(message = "postId should not be null")
    @Min(value = 1, message = "postId can't be negative")
    private Integer postId;

    //optional
    private String flag;
}
