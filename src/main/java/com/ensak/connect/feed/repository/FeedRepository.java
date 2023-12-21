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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
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

    public FeedPageResponseDTO findAllWithSearch(PageRequest pageRequest, String search) {
        EntityManager entityManager = managerFactory.createEntityManager();
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;


        Query query = entityManager.createQuery(
                "SELECT j.id, j.updatedAt, 'JOB_POST' as type FROM JobPost j " +
                        "WHERE LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search) " +  // Case-insensitive search
                        "UNION ALL " +
                        "SELECT q.id, q.updatedAt, 'QUESTION_POST' as type FROM QuestionPost q " +
                        "WHERE LOWER(q.question) LIKE LOWER(:search) " +
                        "ORDER BY updatedAt DESC "
        );
        query.setParameter("search", "%" + search + "%");


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


    public Page<FeedResponceDTO> findAllWithSearchAndFilter(PageRequest pageRequest, String search, String filter) {
        EntityManager entityManager = managerFactory.createEntityManager();
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;

        Query query;
        long totals;

        if (Objects.equals(filter, "Q&A")) {
            query = entityManager.createQuery(
                    "SELECT q, 'QUESTION_POST' as type FROM QuestionPost q " +
                            "WHERE LOWER(q.question) LIKE LOWER(:search)  " +
                            "ORDER BY updatedAt DESC "
            );
            totals = (Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q")
                            .getSingleResult();
        }else {
            query = entityManager.createQuery(
                    "SELECT j, 'JOB_POST' as type FROM JobPost j " +
                            "WHERE LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search) " +
                            "ORDER BY updatedAt DESC "
            );
            totals = (Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j")
                    .getSingleResult();
        }

        query.setParameter("search", "%" + search + "%");


        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Object[]> queryResult = query.getResultList();
        List<FeedResponceDTO> feedResponceDTOList = new ArrayList<>();

        if ("JOB_POST".equals(queryResult.get(0)[1])){
            log.info("On est dans job post");
            for (Object[] row : queryResult) {
                feedResponceDTOList.add(FeedResponceDTO.map((JobPost) row[0]));
            }
        }else {
            log.info("On est dans question post");
            for (Object[] row : queryResult) {
                feedResponceDTOList.add(FeedResponceDTO.map((QuestionPost) row[0]));
            }
        }

        return new PageImpl<>(feedResponceDTOList, pageRequest, totals);
    }
}
