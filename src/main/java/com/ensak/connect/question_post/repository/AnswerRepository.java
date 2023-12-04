package com.ensak.connect.question_post.repository;

import com.ensak.connect.question_post.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
