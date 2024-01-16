package com.ensak.connect.job_post.repository;

import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.question_post.model.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    public boolean existsJobPostByIdAndAuthorId(Integer Id, Integer authorId);

    @Query("SELECT p FROM JobPost p JOIN p.tags t WHERE t = Lower(:tag)")
    List<JobPost> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT p FROM JobPost p JOIN p.tags t WHERE t IN (:tags)")
    List<JobPost> retrieveByTags(@Param("tags") List<String> tags);

    @Query("SELECT j FROM JobPost j WHERE j.id IN :ids ORDER BY j.updatedAt DESC ")
    List<JobPost> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT MONTH(j.createdAt) as month, COUNT(j) as count FROM JobPost j WHERE (YEAR(j.createdAt) = YEAR(current_date) ) GROUP BY MONTH(j.createdAt)")
    List<Object[]> countByMonthOfCurrentYear();

    Optional<List<JobPost>> findByAuthorId(Integer authorId);
}
