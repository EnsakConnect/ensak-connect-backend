package com.ensak.connect.job_post.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.job_post.dto.JobApplicationRequestDTO;
import com.ensak.connect.job_post.dto.JobApplicationResponseDTO;
import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.service.JobApplicationService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.auth.enums.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<JobApplicationResponseDTO> apply(@PathVariable Integer jobPostId, @RequestBody JobApplicationRequestDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        JobApplication application = jobApplicationService
                .createJobApplication(user.getId(), jobPostId, requestDTO.getMessage());
        return new ResponseEntity<>(JobApplicationResponseDTO.mapToDTO(application), HttpStatus.CREATED);
    }

    @PutMapping("/applications")
    public ResponseEntity<JobApplicationResponseDTO> updateApplication(@PathVariable Integer jobPostId, @RequestBody JobApplicationRequestDTO requestDTO) {
        User user = authenticationService.getAuthenticatedUser();
        JobApplication application = jobApplicationService.updateApplication(user.getId(), jobPostId, requestDTO.getMessage());
        return ResponseEntity.ok(JobApplicationResponseDTO.mapToDTO(application));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<JobApplicationResponseDTO>> getApplications(@PathVariable Integer jobPostId, Pageable pageable) throws ForbiddenException {
        User user = authenticationService.getAuthenticatedUser();
        boolean authorized = jobPostService.existsJobPost(user.getId(), jobPostId);

        if (!authorized) {
            throw new ForbiddenException("User does not have access to this job post.");
        }

        List<JobApplication> applications = jobApplicationService.getApplications(jobPostId, pageable);
        List<JobApplicationResponseDTO> applicationsDTO = applications.stream().map(JobApplicationResponseDTO::mapToDTO).toList();
        return ResponseEntity.ok(applicationsDTO);
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