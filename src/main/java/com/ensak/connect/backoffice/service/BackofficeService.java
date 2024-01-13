package com.ensak.connect.backoffice.service;

import com.ensak.connect.backoffice.dto.DashboardAllResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.feed.service.FeedService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.question_post.service.QuestionPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BackofficeService {

    private final FeedService feedService;
    private final ProfileService profileService;
    private final JobPostService jobPostService;
    private final BlogPostService blogPostService;
    private final QuestionPostService questionPostService;

    public List<DashboardAllResponseDTO> countDashboardCharts() {
        DashboardResponseDTO postsCount = feedService.getCountPosts();
        DashboardResponseDTO profilesCount = profileService.countProfileWithProfileType();
        DashboardResponseDTO jobPostMonthly = jobPostService.getCountPostsMonthly();
        DashboardResponseDTO blogPostMonthly = blogPostService.getCountPostsMonthly();
        DashboardResponseDTO questionPostMonthly = questionPostService.getCountPostsMonthly();
        List<DashboardAllResponseDTO> result = new ArrayList<>();
        result.add(
                DashboardAllResponseDTO.builder()
                        .chart("POSTS")
                        .data(postsCount)
                        .build()
        );
        result.add(
                DashboardAllResponseDTO.builder()
                        .chart("USERS")
                        .data(profilesCount)
                        .build()
        );
        result.add(
                DashboardAllResponseDTO.builder()
                        .chart("JOB")
                        .data(jobPostMonthly)
                        .build()
        );
        result.add(
                DashboardAllResponseDTO.builder()
                        .chart("BLOG")
                        .data(blogPostMonthly)
                        .build()
        );
        result.add(
                DashboardAllResponseDTO.builder()
                        .chart("QUESTION")
                        .data(questionPostMonthly)
                        .build()
        );
        return result;
    }
}
