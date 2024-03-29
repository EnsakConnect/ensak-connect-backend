package com.ensak.connect.unit.job_post;

import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.job_post.repository.JobPostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled
public class JobPostServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @InjectMocks
    private JobPostService jobPostService;

    @Test
    public void testSave() {
        JobPostRequestDTO request = new JobPostRequestDTO();
        request.setTitle("Software Engineer");
        request.setDescription("Develop and maintain software applications.");
        jobPostService.createJobPost(request);

        JobPost expectedJobPost = new JobPost();
        expectedJobPost.setTitle("Software Engineer");
        expectedJobPost.setDescription("Develop and maintain software applications.");

        Mockito.verify(jobPostRepository, Mockito.times(1)).save(expectedJobPost);
    }

//    @Test
//    public void testFindAll() {
//        List<JobPostResponseDTO> jobPosts = new ArrayList<>();
//        JobPostResponseDTO jobPost1 = new JobPostResponseDTO();
//        jobPost1.setTitle("Software Engineer");
//        jobPost1.setDescription("Develop and maintain software applications.");
//        jobPosts.add(jobPost1);
//
//        JobPostResponseDTO jobPost2 = new JobPostResponseDTO();
//        jobPost2.setTitle("Product Manager");
//        jobPost2.setDescription("Manage the product development lifecycle.");
//        jobPosts.add(jobPost2);
//
//        Mockito.when(jobPostRepository.findAll()).thenReturn(jobPosts);
//
//        List<JobPostResponseDTO> actualJobPosts = jobPostService.getJobPosts();
//
//        assertThat(actualJobPosts).isEqualTo(jobPosts);
//    }
}
