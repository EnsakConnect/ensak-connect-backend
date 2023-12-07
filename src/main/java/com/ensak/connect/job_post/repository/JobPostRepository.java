package com.ensak.connect.job_post.repository;

import com.ensak.connect.job_post.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

}
