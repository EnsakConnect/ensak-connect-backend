package com.ensak.connect.job_post.service;

import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobApplicationRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public JobApplication updateApplication(Integer userId, Integer jobPostId, String message){
        var application =  jobApplicationRepository.findJobApplicationByApplicantIdAndJobPostId(userId,jobPostId).orElseThrow(
                () -> new NotFoundException("Job Application Not Found")
        );
        application.setMessage(message);

        return jobApplicationRepository.save(application);
    }

    public List<JobApplication> getApplications(Integer jobPostId, Pageable pageable) {
        JobPost jobPost = jobPostService.getJobPostById(jobPostId);
        List<JobApplication> applications =  jobApplicationRepository.findByJobPostId(jobPost.getId(),pageable).getContent();
        return applications;
    }

    public void deleteApplication(Integer jappId){
        jobApplicationRepository.findById(jappId).orElseThrow(
                () -> new NotFoundException("Job Application Not Found")
        );
        jobApplicationRepository.deleteById(jappId);
    }

    public JobApplication getUserApplication(Integer userId, Integer jobPostId){
        return jobApplicationRepository.findJobApplicationByApplicantIdAndJobPostId(userId,jobPostId).orElseThrow(
                () -> new NotFoundException("Job Application Not Found")
        );
    }
}
