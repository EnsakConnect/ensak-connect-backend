package com.ensak.connect.job_post;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final AuthenticationService authenticationService;

    public JobPost getJobPostById(Integer id) {
        return jobPostRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Could not find job post with id "+ id + ".")
        );
    }

    public JobPost createJobPost(JobPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return jobPostRepository.save(
                JobPost.builder()
                        .title()
                        .companyName()
                        .location()
                        .companyType()
                        .category()
                        .description()
                        .tags()
                        .build()
        )
    }

    public void save(JobPostRequestDTO request) {
        var jobPost = JobPost.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createDate(LocalDate.now())
                .build();
        jobPostRepository.save(jobPost);
    }

    public List<JobPost> findAll() {
        return jobPostRepository.findAll();
    }
}
