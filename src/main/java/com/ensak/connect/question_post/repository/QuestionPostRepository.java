package com.ensak.connect.question_post.repository;

import com.ensak.connect.question_post.model.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Integer> {
}
