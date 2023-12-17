package com.ensak.connect.job_post.controller;


import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-posts")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping
    public ResponseEntity<JobPostResponseDTO> create (
            @RequestBody @Valid JobPostRequestDTO request
    ) {
        JobPost jobPost = jobPostService.createJobPost(request);
        return new ResponseEntity<>(JobPostResponseDTO.map(jobPost), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobPostResponseDTO>> getAll() {
        List<JobPost> jobPosts = jobPostService.getJobPosts();
        return ResponseEntity.ok(JobPostResponseDTO.map(jobPosts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostResponseDTO> show(@PathVariable Integer id) {
        JobPost jobPost = jobPostService.getJobPostById(id);
        return ResponseEntity.ok(JobPostResponseDTO.map(jobPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPostResponseDTO> update(@PathVariable Integer id, @RequestBody @Valid JobPostRequestDTO request) {
        JobPost jobPost = jobPostService.updateJobPostById(id, request);
        return new ResponseEntity<>(JobPostResponseDTO.map(jobPost), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        jobPostService.deleteJobPostById(id);
        return ResponseEntity.ok(null);
    }
}
