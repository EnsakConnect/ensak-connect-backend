package com.ensak.connect.blog_post.repository;

import com.ensak.connect.blog_post.model.CommentBlogPost;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentBlogPostRepository extends JpaRepository<CommentBlogPost,Integer> {
}
