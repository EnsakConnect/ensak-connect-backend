package com.ensak.connect.job_post.service;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.job_post.model.CommentPost;
import com.ensak.connect.job_post.repository.CommentPostRepository;
import com.ensak.connect.job_post.dto.CommentPostRequestDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.auth.enums.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentPostService {

    private final CommentPostRepository commentPostRepository;
    private final JobPostService jobPostService;
    private final AuthenticationService authService;

    public List<CommentPost> getCommentsByJobPostId(Integer jobPostId) {
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        return jobPost.getComments();
    }

    public CommentPost createCommentForJobPost(Integer jobPostId, CommentPostRequestDTO request) {
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        User author = authService.getAuthenticatedUser();
        return commentPostRepository.save(
                CommentPost.builder()
                        .content(request.getContent())
                        .jobPost(jobPost)
                        .author(author)
                        .build()
        );
    }

    @SneakyThrows
    public CommentPost updateCommentPostById(Integer jobPostId, Integer commentPostId, CommentPostRequestDTO request) {
        User auth = authService.getAuthenticatedUser();
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        CommentPost commentPost = commentPostRepository.findById(commentPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the requested id.")
        );
        if(!jobPost.getId().equals(commentPost.getJobPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested comment.");
        }
        if(!commentPost.getAuthor().getId().equals(auth.getId())){
            throw new ForbiddenException("Cannot updated the requested comment.");
        }
        commentPost.setContent(request.getContent());
        return commentPostRepository.save(commentPost);
    }

    @SneakyThrows
    public void deleteCommentPostById(Integer jobPostId, Integer commentPostId) {
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        CommentPost commentPost = commentPostRepository.findById(commentPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the requested id.")
        );
        if(!jobPost.getId().equals(commentPost.getJobPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested answer.");
        }
        commentPostRepository.delete(commentPost);
    }
}
