package com.ensak.connect.blog_post.service;

import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.blog_post.model.BlogPostApplication;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostApplicationRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostApplicationService {
    private final BlogPostService blogPostService;
    private final UserService userService;
    private final BlogPostApplicationRepository blogPostApplicationRepository;

    public BlogPostApplication createBlogPostApplication(Integer userId,Integer blogPostId,String message){
        User user = userService.getUserById(userId);
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        BlogPostApplication application = BlogPostApplication.builder()
                .applicant(user)
                .blogPost(blogPost)
                .message(message)
                .build();
        return blogPostApplicationRepository.save(application);
    }

    public  BlogPostApplication updateApplication(Integer userId,Integer blogPostId,String message){
        var application = blogPostApplicationRepository.findBlogPostApplicationByApplicantAndBlogPostId(userId,blogPostId).orElseThrow(
                () -> new NotFoundException("Blog Application Not Found")
        );
        application.setMessage(message);

        return blogPostApplicationRepository.save(application);

    }

    public List<BlogPostApplication> getApplications(Integer blogPostId,Pageable pageable){
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        List<BlogPostApplication> applications =blogPostApplicationRepository.findByBlogPostId(blogPost.getId(),pageable).getContent();
        return applications;
    }

    public void deleteApplication(Integer blogPostId){
        blogPostApplicationRepository.findById(blogPostId).orElseThrow(
                () -> new NotFoundException("Blog Post Application Not Found")
        );
        blogPostApplicationRepository.deleteById(blogPostId);
    }

    public BlogPostApplication getUserApplication(Integer userId,Integer blogPostId){
        return blogPostApplicationRepository.findBlogPostApplicationByApplicantAndBlogPostId(userId,blogPostId).orElseThrow(
                () -> new NotFoundException("Blog Post Application Not Found")
        );

    }
}
