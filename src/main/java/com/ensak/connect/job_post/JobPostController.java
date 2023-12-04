package com.ensak.connect.job_post;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-post/")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping("add")
    public ResponseEntity<?> addJobPost (
            @RequestBody @Valid JobPostRequest request
    ) throws MethodArgumentNotValidException {
        jobPostService.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<JobPost>> findAllJobPosts() {
        return ResponseEntity.ok(jobPostService.findAll());
    }
}