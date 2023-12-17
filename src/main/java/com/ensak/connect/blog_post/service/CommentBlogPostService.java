package com.ensak.connect.blog_post.service;

import  com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.blog_post.dto.CommentBlogPostRequestDTO;
import com.ensak.connect.blog_post.model.CommentBlogPost;
import com.ensak.connect.blog_post.repository.CommentBlogPostRepository;
import com.ensak.connect.blog_post.dto.CommentBlogPostResponseDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentBlogPostService {
    private final CommentBlogPostRepository commentBlogPostRepository;
    private final BlogPostService blogPostService;
    private final AuthenticationService authenticationService;

    public List<CommentBlogPost> getCommentsByBlogPostId(Integer blogPostId) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        return blogPost.getComments();
    }

    public CommentBlogPost createCommentForBlogPost(Integer blogPostId, CommentBlogPostRequestDTO request) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        User author = authenticationService.getAuthenticatedUser();
        return commentBlogPostRepository.save(
                CommentBlogPost.builder()
                        .content(request.getContent())
                        .blogPost(blogPost)
                        .author(author)
                        .build()
        );

    }

    @SneakyThrows
    public CommentBlogPost updateCommentBlogPostById(Integer blogPostId,Integer commentBlogPostId,CommentBlogPostRequestDTO request) {
        User auth = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        CommentBlogPost commentBlogPost = commentBlogPostRepository.findById(commentBlogPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the request id. ")
        );
        if(!blogPost.getId().equals(commentBlogPost.getBlogPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested comment.") ;
        }

        if(!commentBlogPost.getAuthor().getId().equals(auth.getId())) {
            throw new ForbiddenException("Cannot updated the requested comment.");
        }
        commentBlogPost.setContent(request.getContent());
        return commentBlogPostRepository.save(commentBlogPost);
    }

    @SneakyThrows
    public void deleteCommentBlogPostById(Integer blogPstId, Integer commentBlogPostId) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPstId);
        CommentBlogPost commentBlogPost = commentBlogPostRepository.findById(commentBlogPostId).orElseThrow(
                () -> new NotFoundException("Cannot find comment with the request id.")
        );
        if(!blogPost.getId().equals(commentBlogPost.getBlogPost().getId())) {
            throw new ForbiddenException("Connot updated the request ansewer .");
        }
        commentBlogPostRepository.delete(commentBlogPost);
    }
}
