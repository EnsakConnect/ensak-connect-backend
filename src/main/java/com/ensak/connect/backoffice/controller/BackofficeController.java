package com.ensak.connect.backoffice.controller;

import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.backoffice.service.BackofficeService;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.blog_post.service.CommentPostService;
import com.ensak.connect.feed.service.FeedService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.question_post.service.QuestionPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/back-offices")
@RequiredArgsConstructor
public class BackofficeController {

    private final BackofficeService backofficeService;

    @GetMapping
    public ResponseEntity<List<DashboardResponseDTO>> getCountAllDashboardCharts() {
        List<DashboardResponseDTO> result = backofficeService.getBlogPosts();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<BlogPostResponseDTO> create (
            @RequestBody @Valid BlogPostRequestDTO request
    ) {
        BlogPost blogPost = blogPostService.createBlogPost(request);
        return new ResponseEntity<>(BlogPostResponseDTO.map(blogPost), HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDTO> show(@PathVariable Integer id) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        return ResponseEntity.ok(BlogPostResponseDTO.map(blogPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponseDTO> update(@PathVariable Integer id, @RequestBody @Valid BlogPostRequestDTO request) {
        BlogPost blogPost = blogPostService.updateBlogPostById(id, request);
        return new ResponseEntity<>(BlogPostResponseDTO.map(blogPost), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        blogPostService.deleteBlogPostById(id);
        return ResponseEntity.ok(null);
    }
}
