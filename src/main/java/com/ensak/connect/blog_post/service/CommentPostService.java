package com.ensak.connect.blog_post.service;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.model.CommentPost;
import com.ensak.connect.blog_post.repository.CommentPostRepository;
import com.ensak.connect.blog_post.dto.CommentPostRequestDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.job_post.service.JobPostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentPostService {

    private final CommentPostRepository commentPostRepository;
    private final BlogPostService blogPostService;
    private final AuthenticationService authService;

    public List<CommentPost> getCommentsByBlogPostId(Integer blogPostId) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        return blogPost.getComments();
    }

    public CommentPost createCommentForBlogPost(Integer blogPostId, CommentPostRequestDTO request) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        User author = authService.getAuthenticatedUser();
        return commentPostRepository.save(
                CommentPost.builder()
                        .content(request.getContent())
                        .blogPost(blogPost)
                        .author(author)
                        .build()
        );
    }

    @SneakyThrows
    public CommentPost updateCommentPostById(Integer blogPostId, Integer commentPostId, CommentPostRequestDTO request) {
        User auth = authService.getAuthenticatedUser();
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        CommentPost commentPost = commentPostRepository.findById(commentPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the requested id.")
        );
        if(!blogPost.getId().equals(commentPost.getBlogPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested comment.");
        }
        if(!commentPost.getAuthor().getId().equals(auth.getId())){
            throw new ForbiddenException("User does not have access to this comment.");
        }
        commentPost.setContent(request.getContent());
        return commentPostRepository.save(commentPost);
    }

    @SneakyThrows
    public void deleteCommentPostById(Integer blogPostId, Integer commentPostId) {
        User auth = authService.getAuthenticatedUser();
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        CommentPost commentPost = commentPostRepository.findById(commentPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the requested id.")
        );
        if(!blogPost.getId().equals(commentPost.getBlogPost().getId())) {
            throw new ForbiddenException("Cannot delete the requested comment.");
        }
        if(!commentPost.getAuthor().getId().equals(auth.getId())){
            throw new ForbiddenException("User does not have access to this comment.");
        }
        commentPostRepository.delete(commentPost);
    }
}
