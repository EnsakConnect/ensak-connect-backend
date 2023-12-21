package com.ensak.connect.feed.repository;

import com.ensak.connect.feed.dto.FeedListIdResponseDTO;
import com.ensak.connect.feed.dto.FeedPageResponseDTO;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.question_post.model.QuestionPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FeedRepository {

    private final EntityManagerFactory managerFactory;

    public FeedPageResponseDTO findAll(PageRequest pageRequest) {
        EntityManager entityManager = managerFactory.createEntityManager();
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;

        Query query = entityManager.createQuery(
                "SELECT j.id, j.updatedAt, 'JOB_POST' as type FROM JobPost j " +
                        "UNION ALL " +
                        "SELECT q.id, q.updatedAt, 'QUESTION_POST' as type FROM QuestionPost q " +
                        "ORDER BY updatedAt DESC "
        );

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Object[]> queryResult = query.getResultList();

        List<Integer> jobPostIds = new ArrayList<>();
        List<Integer> questionPostIds = new ArrayList<>();

        for (Object[] row : queryResult) {
            if ("JOB_POST".equals(row[2])) {
                jobPostIds.add((Integer) row[0]);
            } else {
                questionPostIds.add((Integer) row[0]);
            }
        }

        FeedListIdResponseDTO feedIds = FeedListIdResponseDTO.builder()
                .jobPostIds(jobPostIds)
                .questionPostIds(questionPostIds)
                .build();

        long totals = (Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j")
                .getSingleResult() +
                (Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q")
                        .getSingleResult();

        return new FeedPageResponseDTO(feedIds, pageRequest, totals);
    }

}
