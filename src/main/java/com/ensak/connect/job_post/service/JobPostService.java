package com.ensak.connect.job_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.resource.ResourceService;
import com.ensak.connect.resource.model.Resource;
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
    private final ResourceService resourceService;

    public JobPost getJobPostById(Integer id) {
        return jobPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find job post with id "+ id + ".")
        );
    }

    public boolean existsJobPost(Integer authorId, Integer jobPostId){
        return jobPostRepository.existsJobPostByIdAndAuthorId(jobPostId, authorId);
    }

    public List<JobPost> getJobPosts() {
        return jobPostRepository.findAll();
    }

    @SneakyThrows
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
                        .resources(resourceService.useResources(request.getResources(),author))
                        .build()
        );
    }

    @SneakyThrows
    @Transactional
    public JobPost updateJobPostById(Integer id, JobPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
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
        jobPost.setResources(resourceService.updateUsedResource(
                jobPost.getResources().stream().map(Resource::getId).toList(),
                request.getResources(),
                author
                )
        );

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
        resourceService.unuseResources(
                jobPost.getResources().stream().map(Resource::getId).toList(),
                author
                );

        jobPostRepository.deleteById(id);
    }

    public List<JobPost> retrieveByTags(List<String> tags) {
        return jobPostRepository.retrieveByTags(tags);
    }

    public DashboardResponseDTO getCountPostsMonthly () {
        return DashboardResponseDTO.mapO(jobPostRepository.countByMonthOfCurrentYear());
    }
}
