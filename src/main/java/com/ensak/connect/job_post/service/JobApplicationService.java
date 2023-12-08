package com.ensak.connect.job_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobApplicationRepository;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobPostService jobPostService;
    private final UserService userService;
    private final JobApplicationRepository jobApplicationRepository;


    public JobApplication createJobApplication(Integer userId, Integer jobPostId,String message){
        User user = userService.getUserById(userId);
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        JobApplication application =  JobApplication.builder()
                .applicant(user)
                .jobPost(jobPost)
                .message(message)
                .build();
        return jobApplicationRepository.save(application);
    }

    public List<JobApplication> getApplications(Integer jobPostId) {
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        return jobApplicationRepository.findJobApplicationByJobPostId(jobPost.getId()).orElse(Collections.emptyList());
    }
}
