package com.ensak.connect.feed.repository;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardSingleObjectDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.feed.dto.FeedListIdResponseDTO;
import com.ensak.connect.feed.dto.FeedPageResponseDTO;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.question_post.model.QuestionPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
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


    @PersistenceContext
    private EntityManager entityManager;
    private final AuthenticationService authenticationService;

    public FeedPageResponseDTO findAll(PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;

        Query query = entityManager.createQuery(
                        "SELECT result.id, result.updatedAt, result.type FROM ( " +
                                "SELECT j.id as id, j.updatedAt as updatedAt, 'JOB_POST' as type FROM JobPost j " +
                                "UNION ALL " +
                                "SELECT q.id as id, q.updatedAt as updatedAt, 'QUESTION_POST' as type FROM QuestionPost q " +
                                "UNION ALL " +
                                "SELECT b.id as id, b.updatedAt as updatedAt, 'BLOG_POST' as type FROM BlogPost b) result " +
                                "ORDER BY result.updatedAt DESC "
                )
                .setFirstResult(offset)
                .setMaxResults(pageSize);


        List<Object[]> queryResult = query.getResultList();

        List<Integer> jobPostIds = new ArrayList<>();
        List<Integer> questionPostIds = new ArrayList<>();
        List<Integer> blogPostIds = new ArrayList<>();

        if (!queryResult.isEmpty()) {
            for (Object[] row : queryResult) {
                if ("JOB_POST".equals(row[2])) {
                    jobPostIds.add((Integer) row[0]);
                } else if ("QUESTION_POST".equals(row[2])) {
                    questionPostIds.add((Integer) row[0]);
                } else {
                    blogPostIds.add((Integer) row[0]);
                }
            }
        }

        FeedListIdResponseDTO feedIds = FeedListIdResponseDTO.builder()
                .jobPostIds(jobPostIds)
                .questionPostIds(questionPostIds)
                .blogPostIds(blogPostIds)
                .build();
        log.warn("ids {}", feedIds);

        long totals = (Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j")
                .getSingleResult() +
                (Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q")
                        .getSingleResult() +
                (Long) entityManager.createQuery("SELECT COUNT(b) FROM BlogPost b")
                        .getSingleResult();

        return new FeedPageResponseDTO(feedIds, pageRequest, totals);
    }

    public FeedPageResponseDTO findAllWithSearch(PageRequest pageRequest, String search) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;


        Query query = entityManager.createQuery(
                "SELECT result.id, result.updatedAt, result.type FROM (" +
                        "SELECT j.id as id, j.updatedAt as updatedAt, 'JOB_POST' as type FROM JobPost j " +
                        "WHERE (LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search)) " +
                        "UNION ALL " +
                        "SELECT q.id as id, q.updatedAt as updatedAt, 'QUESTION_POST' as type FROM QuestionPost q " +
                        "WHERE (LOWER(q.question) LIKE LOWER(:search))" +
                        "UNION ALL " +
                        "SELECT b.id as id, b.updatedAt as updatedAt, 'BLOG_POST' as type FROM BlogPost b " +
                        "WHERE (LOWER(b.content) LIKE LOWER(:search))) result " +
                        "ORDER BY result.updatedAt DESC"
        );
        query.setParameter("search", "%" + search + "%");


        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Object[]> queryResult = query.getResultList();

        List<Integer> jobPostIds = new ArrayList<>();
        List<Integer> questionPostIds = new ArrayList<>();
        List<Integer> blogPostIds = new ArrayList<>();

        if (!queryResult.isEmpty()) {
            for (Object[] row : queryResult) {
                if ("JOB_POST".equals(row[2])) {
                    jobPostIds.add((Integer) row[0]);
                } else if ("QUESTION_POST".equals(row[2])) {
                    questionPostIds.add((Integer) row[0]);
                } else {
                    blogPostIds.add((Integer) row[0]);
                }
            }
        }


        FeedListIdResponseDTO feedIds = FeedListIdResponseDTO.builder()
                .jobPostIds(jobPostIds)
                .questionPostIds(questionPostIds)
                .blogPostIds(blogPostIds)
                .build();

        long totals = (Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j " +
                        "WHERE (LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search)) ")
                .setParameter("search", "%" + search + "%")
                .getSingleResult() +
                (Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q " +
                                "WHERE (LOWER(q.question) LIKE LOWER(:search))")
                        .setParameter("search", "%" + search + "%")
                        .getSingleResult() +
                (Long) entityManager.createQuery("SELECT COUNT(b) FROM BlogPost b " +
                                "WHERE (LOWER(b.content) LIKE LOWER(:search))")
                        .setParameter("search", "%" + search + "%")
                        .getSingleResult();

        return new FeedPageResponseDTO(feedIds, pageRequest, totals);
    }


    public Page<FeedResponceDTO> findAllWithSearchAndFilter(PageRequest pageRequest, String search, String filter) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        int offset = pageNumber * pageSize;

        Query query;
        long totals;

        if (filter.equals("Q&A")) {
            query = entityManager.createQuery(
                    "SELECT q, 'QUESTION_POST' as type FROM QuestionPost q " +
                            "WHERE LOWER(q.question) LIKE LOWER(:search) " +
                            "ORDER BY q.updatedAt DESC "
            );
            totals = (Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q " +
                            "WHERE LOWER(q.question) LIKE LOWER(:search) ")
                    .setParameter("search", "%" + search + "%")
                    .getSingleResult();
        } else if (filter.equals("BlogPost")) {
            query = entityManager.createQuery(
                    "SELECT b, 'BLOG_POST' as type FROM BlogPost b " +
                            "WHERE LOWER(b.content) LIKE LOWER(:search) " +
                            "ORDER BY b.updatedAt DESC "
            );
            totals = (Long) entityManager.createQuery("SELECT COUNT(b) FROM BlogPost b " +
                            "WHERE LOWER(b.content) LIKE LOWER(:search) ")
                    .setParameter("search", "%" + search + "%")
                    .getSingleResult();
        } else {
            query = entityManager.createQuery(
                    "SELECT j, 'JOB_POST' as type FROM JobPost j " +
                            "WHERE (LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search)) " +
                            "AND (LOWER(j.category) = LOWER(:filter) ) " +
                            "ORDER BY j.updatedAt DESC "
            ).setParameter("filter", filter);
            totals = (Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j " +
                            "WHERE (LOWER(j.title) LIKE LOWER(:search) OR LOWER(j.description) LIKE LOWER(:search)) " +
                            "AND (LOWER(j.category) = LOWER(:filter) )")

                    .setParameter("search", "%" + search + "%")
                    .setParameter("filter", filter)
                    .getSingleResult();
        }

        query.setParameter("search", "%" + search + "%");


        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Object[]> queryResult = query.getResultList();
        List<FeedResponceDTO> feedResponceDTOList = new ArrayList<>();

        if (!queryResult.isEmpty()) {
            User author = authenticationService.getAuthenticatedUser();
            if ("JOB_POST".equals(queryResult.get(0)[1])) {
                log.info("On est dans job post");
                for (Object[] row : queryResult) {
                    feedResponceDTOList.add(FeedResponceDTO.map((JobPost) row[0], author.getId()));
                }
            } else if ("BLOG_POST".equals(queryResult.get(0)[1])) {
                log.info("On est dans blog post");
                for (Object[] row : queryResult) {
                    feedResponceDTOList.add(FeedResponceDTO.map((BlogPost) row[0], author.getId()));
                }
            } else {
                log.info("On est dans question post");
                for (Object[] row : queryResult) {
                    feedResponceDTOList.add(FeedResponceDTO.map((QuestionPost) row[0], author.getId()));
                }
            }
        }


        return new PageImpl<>(feedResponceDTOList, pageRequest, totals);
    }

    public DashboardResponseDTO countPosts() {
        List<DashboardSingleObjectDTO> list = new ArrayList<>();
        list.add(DashboardSingleObjectDTO.builder()
                .field("JOB")
                .count((Long) entityManager.createQuery("SELECT COUNT(j) FROM JobPost j")
                        .getSingleResult())
                .build());
        list.add(DashboardSingleObjectDTO.builder()
                .field("BLOG")
                .count((Long) entityManager.createQuery("SELECT COUNT(b) FROM BlogPost b")
                        .getSingleResult())
                .build());
        list.add(DashboardSingleObjectDTO.builder()
                .field("Q&A")
                .count((Long) entityManager.createQuery("SELECT COUNT(q) FROM QuestionPost q")
                        .getSingleResult())
                .build());


        return DashboardResponseDTO.map(list);
    }
}
