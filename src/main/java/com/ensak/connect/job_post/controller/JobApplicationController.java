package com.ensak.connect.job_post.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.job_post.dto.JobApplicationRequestDTO;
import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.service.JobApplicationService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/job-posts/{jobPostId}")
@RequiredArgsConstructor
public class JobApplicationController {
    private final AuthenticationService authenticationService;
    private final JobApplicationService jobApplicationService;
    private final JobPostService jobPostService;

    @PostMapping("/applications")
    public ResponseEntity<JobApplication> apply(@PathVariable Integer jobPostId, @RequestBody JobApplicationRequestDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        JobApplication application = jobApplicationService
                .createJobApplication(user.getId(), jobPostId, requestDTO.getMessage());
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @PutMapping("/applications")
    public ResponseEntity<JobApplication> updateApplication(@PathVariable Integer jobPostId, @RequestBody JobApplicationRequestDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        JobApplication application = jobApplicationService.updateApplication(user.getId(), jobPostId, requestDTO.getMessage());
        return ResponseEntity.ok(application);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<JobApplication>> getApplications(@PathVariable Integer jobPostId) throws ForbiddenException {
        User user = authenticationService.getAuthenticatedUser();
        boolean authorized = jobPostService.existsJobPost(user.getId(), jobPostId);

        if (!authorized) {
            throw new ForbiddenException("User does not have access to this job post.");
        }

        List<JobApplication> applications = jobApplicationService.getApplications(jobPostId);
        return ResponseEntity.ok(applications);
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable Integer jobPostId, @PathVariable Integer applicationId) throws ForbiddenException {
        User user = authenticationService.getAuthenticatedUser();

        boolean authorized = jobPostService.existsJobPost(user.getId(), jobPostId);

        if (!authorized) {
            throw new ForbiddenException("User does not have access to this job post.");
        }

        jobApplicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();

    }
}