package com.ensak.connect.job_post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public void save(JobPostRequest request) {
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
