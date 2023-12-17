package com.ensak.connect.blog_post.repository;

import com.ensak.connect.blog_post.model.BlogPostApplication;
import com.ensak.connect.blog_post.model.BlogPost;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BlogPostApplicationRepository extends JpaRepository<BlogPostApplication,Integer> {

    public  Optional<List<BlogPostApplication>> findByBlogPostId(Integer blogPostId);
    Page<BlogPostApplication> findByBlogPostId(Integer blogPostId, Pageable pageable);

    public Optional<BlogPostApplication> findBlogPostApplicationByApplicantAndBlogPostId(Integer applicantId,Integer blogPostId);

}
