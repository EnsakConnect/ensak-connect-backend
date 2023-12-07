package com.ensak.connect.job_post.controller;


import com.ensak.connect.job_post.model.CommentPost;
import com.ensak.connect.job_post.dto.CommentPostRequestDTO;
import com.ensak.connect.job_post.dto.CommentPostResponseDTO;
import com.ensak.connect.job_post.service.CommentPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/job-posts/{job_post_id}/comments")
@RequiredArgsConstructor
public class CommentPostController {

    private final CommentPostService commentPostService;

    @GetMapping
    public  ResponseEntity<List<CommentPostResponseDTO>> getJobPostComments(
            @PathVariable Integer job_post_id
    ) {
        return new ResponseEntity<>(
                commentPostService.getCommentsByJobPostId(job_post_id).stream().map(
                        CommentPostResponseDTO::map
                ).toList(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CommentPostResponseDTO> create(
            @PathVariable Integer job_post_id,
            @RequestBody @Valid CommentPostRequestDTO request
    ) {
        CommentPost commentPost = commentPostService.createCommentForJobPost(job_post_id, request);
        return new ResponseEntity<>(CommentPostResponseDTO.map(commentPost), HttpStatus.CREATED);
    }

    @PostMapping("/{comment_post_id}")
    public ResponseEntity<CommentPostResponseDTO> update(
            @PathVariable Integer job_post_id,
            @PathVariable Integer comment_post_id,
            @RequestBody @Valid CommentPostRequestDTO request
    ) {
        CommentPost commentPost = commentPostService.updateCommentPostById(job_post_id, comment_post_id, request);
        return new ResponseEntity<>(CommentPostResponseDTO.map(commentPost), HttpStatus.OK);
    }

    @DeleteMapping("/comment_post_id")
    public ResponseEntity<Object> delete(
            @PathVariable Integer job_post_id,
            @PathVariable Integer comment_post_id
    ) {
        commentPostService.deleteCommentPostById(job_post_id, comment_post_id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
