package com.ensak.connect.question_post.repository;

import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.question_post.model.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Integer> {

    @Query("SELECT q FROM QuestionPost q JOIN q.tags t WHERE t = Lower(:tag)")
    List<QuestionPost> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT q FROM QuestionPost q JOIN q.tags t WHERE t IN (:tags)")
    List<QuestionPost> retrieveByTags(@Param("tags") List<String> tags);

    @Query("SELECT q FROM QuestionPost q WHERE q.id IN :ids ORDER BY q.updatedAt DESC ")
    List<QuestionPost> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT MONTH(q.createdAt) as month, COUNT(q) as count FROM QuestionPost q WHERE (YEAR(q.createdAt) = YEAR(current_date) ) GROUP BY MONTH(q.createdAt)")
    List<Object[]> countByMonthOfCurrentYear();

    Optional<List<QuestionPost>> findByAuthorId(Integer authorId);

}
