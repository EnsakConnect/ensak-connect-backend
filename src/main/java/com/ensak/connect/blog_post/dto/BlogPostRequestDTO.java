package com.ensak.connect.blog_post.dto;

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
public class BlogPostRequestDTO {

    @NotBlank(message = "Content should not be blank")
    private String content;

    @Size(max = 10, message = "You can have a maximum of 10 tags")
    private List<String> tags = new ArrayList<>();

    @Size(max = 10, message = "You can have a maximum of 10 resources")
    private List<Integer> resources = new ArrayList<>();
}
