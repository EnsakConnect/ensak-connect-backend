package com.ensak.connect.blog_post.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostRequestDTO {
    @NotBlank(message = "Comment should not be blank")
    private String content;
}
