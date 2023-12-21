package com.ensak.connect.feed.service;


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

    public Page<FeedResponceDTO> getPageOfFeed(PageRequest pageRequest) {
        FeedPageResponseDTO feedPageResponse = feedRepository.findAll(pageRequest);
        List<JobPost> jobPosts = jobPostRepository.findAllByIds(feedPageResponse.getListIds().getJobPostIds());
        List<FeedResponceDTO> jobPostList = FeedResponceDTO.mapJobPosts(jobPosts);
        List<QuestionPost> questionPosts = questionPostRepository.findAllByIds(feedPageResponse.getListIds().getQuestionPostIds());
        List<FeedResponceDTO> questionPostList = FeedResponceDTO.mapQuestionPosts(questionPosts);
        List<FeedResponceDTO> feedResponse = Stream.concat(jobPostList.stream(), questionPostList.stream())
                .sorted(Comparator.comparing(FeedResponceDTO::getUpdatedAt).reversed())
                .toList();
        return new PageImpl<>(feedResponse, feedPageResponse.getPage(), feedPageResponse.getTotals());
    }
}
