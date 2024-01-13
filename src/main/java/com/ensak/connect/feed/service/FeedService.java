package com.ensak.connect.feed.service;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.feed.dto.FeedPageResponseDTO;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.feed.repository.FeedRepository;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final JobPostRepository jobPostRepository;
    private final QuestionPostRepository questionPostRepository;
    private final BlogPostRepository blogPostRepository;
    private final AuthenticationService authenticationService;

    public Page<FeedResponceDTO> getPageOfFeed(PageRequest pageRequest) {
        FeedPageResponseDTO feedPageResponse = feedRepository.findAll(pageRequest);
        List<FeedResponceDTO> feedResponse = getPageFromIds(feedPageResponse);
        return new PageImpl<>(feedResponse, feedPageResponse.getPage(), feedPageResponse.getTotals());
    }

    public Page<FeedResponceDTO> getPageOfFeedWithSearchAndFilter(PageRequest pageRequest, String search, String filter) {
        if (filter.equals("ALL")) {
            if (search.isEmpty()){
                return getPageOfFeed(pageRequest);
            }
            FeedPageResponseDTO feedPageResponse = feedRepository.findAllWithSearch(pageRequest, search);
            List<FeedResponceDTO> feedResponse = getPageFromIds(feedPageResponse);
            return new PageImpl<>(feedResponse, feedPageResponse.getPage(), feedPageResponse.getTotals());
        }else {
            return feedRepository.findAllWithSearchAndFilter(pageRequest, search, filter);
        }
    }

    public List<FeedResponceDTO> getPageFromIds(FeedPageResponseDTO pageRequest) {
        User author = authenticationService.getAuthenticatedUser();
        List<JobPost> jobPosts = jobPostRepository.findAllByIds(pageRequest.getListIds().getJobPostIds());
        List<FeedResponceDTO> jobPostList = FeedResponceDTO.mapJobPosts(jobPosts, author.getId());
        List<QuestionPost> questionPosts = questionPostRepository.findAllByIds(pageRequest.getListIds().getQuestionPostIds());
        List<FeedResponceDTO> questionPostList = FeedResponceDTO.mapQuestionPosts(questionPosts, author.getId());
        List<BlogPost> blogPosts = blogPostRepository.findAllByIds(pageRequest.getListIds().getBlogPostIds());
        List<FeedResponceDTO> blogPostList = FeedResponceDTO.mapBlogPosts(blogPosts, author.getId());
        List<FeedResponceDTO> result = Stream.concat(jobPostList.stream(), questionPostList.stream())
                .toList();
        return Stream.concat(blogPostList.stream(), result.stream())
                .sorted(Comparator.comparing(FeedResponceDTO::getUpdatedAt).reversed())
                .toList();
    }

    public DashboardResponseDTO getCountPosts () {
        return feedRepository.countPosts();
    }
}
