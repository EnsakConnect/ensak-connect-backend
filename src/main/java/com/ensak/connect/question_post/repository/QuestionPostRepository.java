package com.ensak.connect.question_post.repository;

import com.ensak.connect.question_post.model.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Integer> {

    @Query("SELECT q FROM QuestionPost q JOIN q.tags t WHERE t = Lower(:tag)")
    List<QuestionPost> retrieveByTag(@Param("tag") String tag);
}
