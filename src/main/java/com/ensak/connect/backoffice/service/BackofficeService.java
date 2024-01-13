package com.ensak.connect.backoffice.service;

import com.ensak.connect.auth.dto.UserResponseDTO;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.backoffice.dto.DashboardAllResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.feed.dto.FeedPageResponseDTO;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.feed.service.FeedService;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.service.QuestionPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    private final UserService userService;

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


    public Page<UserResponseDTO> getPageOfUsers(PageRequest pageRequest) {
        return userService.getUsersPage(pageRequest);
    }



}
