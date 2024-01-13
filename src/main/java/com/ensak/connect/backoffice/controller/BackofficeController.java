package com.ensak.connect.backoffice.controller;

import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.backoffice.dto.DashboardAllResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.backoffice.service.BackofficeService;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.blog_post.service.CommentPostService;
import com.ensak.connect.feed.service.FeedService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.question_post.service.QuestionPostService;
import com.ensak.connect.report.Report;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/back-offices")
@RequiredArgsConstructor
public class BackofficeController {

    private final BackofficeService backofficeService;

    @GetMapping("/dashboard")
    public ResponseEntity<List<DashboardAllResponseDTO>> getCountAllDashboardCharts() {
        return ResponseEntity.ok(backofficeService.countDashboardCharts());
    }



}
