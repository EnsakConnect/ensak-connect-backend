package com.ensak.connect.blog_post.controller;


import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.service.JobPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog-posts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<BlogPostResponseDTO> create (
            @RequestBody @Valid BlogPostRequestDTO request
    ) {
        return new ResponseEntity<>(blogPostService.createBlogPost(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostResponseDTO>> getAll() {
        return ResponseEntity.ok(blogPostService.getBlogPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDTO> show(@PathVariable Integer id) {
        return ResponseEntity.ok(blogPostService.getBlogPostByIdMapped(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponseDTO> update(@PathVariable Integer id, @RequestBody @Valid BlogPostRequestDTO request) {
        return new ResponseEntity<>(blogPostService.updateBlogPostById(id, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        blogPostService.deleteBlogPostById(id);
        return ResponseEntity.ok(null);
    }
}
