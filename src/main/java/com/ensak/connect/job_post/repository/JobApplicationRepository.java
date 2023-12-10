package com.ensak.connect.job_post.repository;

import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.model.JobPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
    public Optional<List<JobApplication>> findByJobPostId(Integer jobPostId);
    Page<JobApplication> findByJobPostId(Integer jobPostId, Pageable pageable);

    public Optional<JobApplication> findJobApplicationByApplicantIdAndJobPostId(Integer applicantId, Integer jobPostId);
}
