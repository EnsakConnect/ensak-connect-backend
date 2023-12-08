package com.ensak.connect.job_post.repository;

import com.ensak.connect.job_post.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostRepository extends JpaRepository<CommentPost, Integer> {
}
