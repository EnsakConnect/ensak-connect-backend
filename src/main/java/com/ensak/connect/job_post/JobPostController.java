package com.ensak.connect.job_post;


import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-posts")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping
    public ResponseEntity<?> addJobPost (
            @RequestBody @Valid JobPostRequestDTO request
    ) {
        jobPostService.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<JobPost>> findAllJobPosts() {
        return ResponseEntity.ok(jobPostService.findAll());
    }
}
