package com.ensak.connect.job_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.question_post.model.QuestionPost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final AuthenticationService authenticationService;

    public JobPost getJobPostById(Integer id) {
        return jobPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find job post with id "+ id + ".")
        );
    }

    public boolean existsJobPost(Integer authorId, Integer jobPostId){
        return jobPostRepository.existsJobPostByIdAndAuthorId(authorId,jobPostId);
    }

    public List<JobPost> getJobPosts() {
        return jobPostRepository.findAll();
    }

    public JobPost createJobPost(JobPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return jobPostRepository.save(
                JobPost.builder()
                        .title(request.getTitle())
                        .companyName(request.getCompanyName())
                        .location(request.getLocation())
                        .author(author)
                        .companyType(request.getCompanyType())
                        .category(request.getCategory())
                        .description(request.getDescription())
                        .tags(request.getTags())
                        .build()
        );
    }

    @Transactional
    public JobPost updateJobPostById(Integer id, JobPostRequestDTO request) {

        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find job post with id " + id + ".")
        );

        jobPost.setTitle(request.getTitle());
        jobPost.setCompanyName(request.getCompanyName());
        jobPost.setLocation(request.getLocation());
        jobPost.setCompanyType(request.getCompanyType());
        jobPost.setCategory(request.getCategory());
        jobPost.setDescription(request.getDescription());
        jobPost.setTags(request.getTags());

        return jobPostRepository.save(jobPost);
    }

    @SneakyThrows
    public void deleteJobPostById(Integer id) {

        User author = authenticationService.getAuthenticatedUser();
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find job post with id " + id + ".")
        );

        if (!author.getId().equals(jobPost.getAuthor().getId())) {
            throw new ForbiddenException("Cannot delete posts made by other users");
        }

        jobPostRepository.deleteById(id);
    }

    public List<QuestionPost> retrieveByTags(List<String> tags) {
        return jobPostRepository.retrieveByTags(tags);
    }
}
