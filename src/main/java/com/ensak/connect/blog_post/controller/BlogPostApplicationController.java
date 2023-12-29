package com.ensak.connect.blog_post.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.blog_post.dto.BlogPostApplicationResponseDTO;
import com.ensak.connect.blog_post.dto.BlogPostApplicationRequestDTO;
import com.ensak.connect.blog_post.model.BlogPostApplication;
import com.ensak.connect.blog_post.service.BlogPostApplicationService;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blog-posts/{blogPostId}")
@RequiredArgsConstructor

public class BlogPostApplicationController {
    private final AuthenticationService authenticationService;
    private final BlogPostApplicationService blogPostApplicationService;
    private final BlogPostService blogPostService;

    @PostMapping("/applications")
    public ResponseEntity<BlogPostApplicationResponseDTO> apply(@PathVariable Integer blogPostId, @RequestBody BlogPostApplicationRequestDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        BlogPostApplication application = blogPostApplicationService.createBlogPostApplication(user.getId(),blogPostId,requestDTO.getMessage());
        return new ResponseEntity<>(BlogPostApplicationResponseDTO.mapToDTO(application),HttpStatus.CREATED);
    }

    @PutMapping("/applications")
    public ResponseEntity<BlogPostApplicationResponseDTO> updateApplication(@PathVariable Integer blogPostId,@RequestBody BlogPostApplicationResponseDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        BlogPostApplication application = blogPostApplicationService.updateApplication(user.getId(), blogPostId,requestDTO.getMessage());
        return  ResponseEntity.ok(BlogPostApplicationResponseDTO.mapToDTO(application));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<BlogPostApplicationResponseDTO>> getApplications(@PathVariable Integer blogPostId, Pageable pageable) throws  ForbiddenException {
        User user = authenticationService.getAuthenticatedUser();
        boolean authorized = blogPostService.existBlogPost(user.getId(),blogPostId);

        if (!authorized) {
            throw new ForbiddenException("User does not have access to this blog post.");

        }

        List<BlogPostApplication> applications = blogPostApplicationService.getApplications(blogPostId,pageable);
        List<BlogPostApplicationResponseDTO> applicationsDTO = applications.stream().map(BlogPostApplicationResponseDTO::mapToDTO).toList();
        return ResponseEntity.ok(applicationsDTO);
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable Integer bloPostId, @PathVariable Integer applicationId) throws  ForbiddenException {
        User  user = authenticationService.getAuthenticatedUser();

        boolean authorized = blogPostService.existBlogPost(user.getId(),bloPostId);

        if (!authorized) {
            throw  new ForbiddenException("User does not have access to this blog post.");
        }
        blogPostApplicationService.deleteApplication(applicationId);
        return  ResponseEntity.noContent().build();
    }

}
