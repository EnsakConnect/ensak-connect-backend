package com.ensak.connect.unit.job_post;

import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.job_post.controller.JobPostController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JobPostControllerTest {

    @Mock
    private JobPostService jobPostService;

    @InjectMocks
    private JobPostController jobPostController;

    @Test
    public void testAddJobPost_validRequest_returnsAccepted() throws MethodArgumentNotValidException {
        JobPostRequestDTO request = new JobPostRequestDTO();
        request.setTitle("Software Engineer");
        request.setDescription("Develop and maintain software applications.");

        ResponseEntity<?> responseEntity = jobPostController.create(request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        Mockito.verify(jobPostService, Mockito.times(1)).createJobPost(request);
    }

//    @Test
//    public void testAddJobPost_invalidRequest_throwsMethodArgumentNotValidException() {
//        JobPostRequest request = new JobPostRequest();
//        request.setTitle("");
//        request.setDescription("Develop and maintain software applications.");
//
//        assertThrows(MethodArgumentNotValidException.class, () -> jobPostController.addJobPost(request));
//        Mockito.verify(jobPostService, Mockito.times(0)).save(request);
//    }


    @Test
    public void testFindAllJobPosts_returnsOk() {
        List<JobPost> jobPosts = new ArrayList<>();
        JobPost jobPost1 = new JobPost();
        jobPost1.setTitle("Software Engineer");
        jobPost1.setDescription("Develop and maintain software applications.");
        jobPosts.add(jobPost1);

        JobPost jobPost2 = new JobPost();
        jobPost2.setTitle("Product Manager");
        jobPost2.setDescription("Manage the product development lifecycle.");
        jobPosts.add(jobPost2);

        Mockito.when(jobPostService.getJobPosts()).thenReturn(jobPosts);

        ResponseEntity<List<JobPost>> responseEntity = jobPostController.getAll();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(jobPosts);
    }
}