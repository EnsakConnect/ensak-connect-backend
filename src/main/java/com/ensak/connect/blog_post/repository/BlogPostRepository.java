package com.ensak.connect.blog_post.repository;

import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.job_post.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    public boolean existsBlogPostByIdAndAuthorId(Integer Id, Integer authorId);

    @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t = Lower(:tag)")
    List<BlogPost> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t IN (:tags)")
    List<BlogPost> retrieveByTags(@Param("tags") List<String> tags);

    @Query("SELECT j FROM BlogPost j WHERE j.id IN :ids ORDER BY j.updatedAt DESC ")
    List<BlogPost> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT MONTH(b.createdAt) as month, COUNT(b) as count FROM BlogPost b WHERE (YEAR(b.createdAt) = YEAR(current_date) ) GROUP BY MONTH(b.createdAt)")
    List<Object[]> countByMonthOfCurrentYear();

    Optional<List<BlogPost>> findByAuthorId(Integer authorId);

}
