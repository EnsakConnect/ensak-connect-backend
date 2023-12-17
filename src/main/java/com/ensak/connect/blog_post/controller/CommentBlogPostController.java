package com.ensak.connect.blog_post.controller;

import com.ensak.connect.blog_post.model.CommentBlogPost;
import com.ensak.connect.blog_post.dto.CommentBlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.CommentBlogPostResponseDTO;
import com.ensak.connect.blog_post.service.CommentBlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blog-posts/{blog_post_id}/comments")
@RequiredArgsConstructor
public class CommentBlogPostController {
    private final CommentBlogPostService commentBlogPostService;

    @GetMapping
    public ResponseEntity<List<CommentBlogPostResponseDTO>> getBlogPostComments(
            @PathVariable Integer blog_post_id
    ) {
        return new ResponseEntity<>(
                commentBlogPostService.getCommentsByBlogPostId(blog_post_id).stream().map(
                        CommentBlogPostResponseDTO::map
                ).toList(),HttpStatus.OK
        );
    }

    @PostMapping("/{comment_blog_post_id}")
    public ResponseEntity<CommentBlogPostResponseDTO> create(
            @PathVariable Integer blog_post_id,
            @RequestBody @Valid CommentBlogPostRequestDTO request
    ) {
        CommentBlogPost commentBlogPost = commentBlogPostService.createCommentForBlogPost(blog_post_id,request);
        return new ResponseEntity<>(CommentBlogPostResponseDTO.map(commentBlogPost),HttpStatus.OK);
    }

    @PutMapping("/{comment_blog_post_id}")
    public ResponseEntity<CommentBlogPostResponseDTO> update(
            @PathVariable Integer blog_post_id,
            @PathVariable Integer comment_blog_post_id,
            @RequestBody @Valid CommentBlogPostRequestDTO request
    ) {
        CommentBlogPost commentBlogPost = commentBlogPostService.updateCommentBlogPostById(blog_post_id,comment_blog_post_id,request);
        return new ResponseEntity<>(CommentBlogPostResponseDTO.map(commentBlogPost),HttpStatus.OK);
    }

    @DeleteMapping("/{comment_blog_post_id}")
    public ResponseEntity<Object> delete(
            @PathVariable Integer blog_post_id,
            @PathVariable Integer comment_blog_post_id
    ) {
        commentBlogPostService.deleteCommentBlogPostById(blog_post_id,comment_blog_post_id);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }




}
