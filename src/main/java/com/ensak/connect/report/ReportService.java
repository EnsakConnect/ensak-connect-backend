package com.ensak.connect.report;

import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.blog_post.service.CommentPostService;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.service.AnswerService;
import com.ensak.connect.question_post.service.QuestionPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    final private ReportRepository reportRepository;
    final private JobPostService jobPostService;
    final private BlogPostService blogPostService;
    final private AnswerService answerService;
    final private QuestionPostService questionPostService;
    final private CommentPostService commentPostService;


    private Object getPost(PostType postType, Integer postId){
        Object post = null;
        switch (postType){
            case JOB -> {
                post = jobPostService.getJobPostById(postId);
            }
            case BLOG -> {
                post = blogPostService.getBlogPostById(postId);
            }
            case ANSWER -> {
                post = answerService.getAnswerById(postId);
            }
            case QUESTION -> {
                post = questionPostService.getQuestionPostById(postId);
            }
            case COMMENT -> {
                post = commentPostService.getCommentById(postId);
            }
        }
        return post;
    }

    private boolean postExist(PostType postType, Integer postId){
        return getPost(postType,postId) != null;
    }

    public void createService(ReportRequestDTO request) {

        if(!postExist(PostType.valueOf(request.getPostType()), request.getPostId())){
            throw new NotFoundException("Post/Comment Not found");
        }

        Report report = Report.builder()
                .flag(request.getFlag())
                .postType(PostType.valueOf(request.getPostType()))
                .postId(request.getPostId())
                .build();

        reportRepository.save(report);
    }

    public Page<Report> getReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    public void deleteReport(Integer reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                ()-> new NotFoundException("Report Not Found")
        );
        reportRepository.delete(report);
    }
}
