package com.ensak.connect.blog_post.repository;

import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.question_post.model.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost,Integer>{

    public boolean existsBlogPostByIdAndAuthorId(Integer id, Integer authorId);

    @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t = Lower(:tag)")
    List<QuestionPost> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t IN (:tags)")
    List<QuestionPost> retrieveByTags(@Param("tags") List<String> tags);

}
