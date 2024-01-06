package com.ensak.connect.integration.job_post;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.job_post.dto.JobApplicationRequestDTO;
import com.ensak.connect.job_post.dto.JobApplicationResponseDTO;
import com.ensak.connect.job_post.model.JobApplication;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobApplicationRepository;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JobApplicationIntegrationTest extends AuthenticatedBaseIntegrationTest {

    public static final String API_JOB_APPLICATION = "/api/v1/job-posts";

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @AfterEach
    void tearDown() {
        jobPostRepository.deleteAll();
        jobApplicationRepository.deleteAll();
    }

    @BeforeEach
    void createJobPost() {
        userTest = this.createDummyUser();
        jobPostTest = jobPostRepository.save(JobPost.builder()
                .title("This is a job title test")
                .author(userTest)
                .description("This is a job post description test")
                .companyName("This is the companyName test")
                .location("This is the location test")
                .companyType("This is the companyType test")
                .category("This is the category test")
                .tags(List.of(new String[]{"test1", "test2"}))
                .build());
    }

    JobPost jobPostTest = new JobPost();
    User userTest = new User();

    JobApplicationRequestDTO jobApplicationTest = JobApplicationRequestDTO.builder()
            .message("This is a message for a job application test")
            .build();


    @Test
    public void itShouldCreateJobApplicationWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString(jobApplicationTest);
        String response = api.perform(
                        post(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }


    @Test
    public void itShouldNotCreateJobApplicationWhenNotAuthenticated() throws Exception {
        String requestJSON = objectMapper.writeValueAsString(jobApplicationTest);
        String response = api.perform(
                        post(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotCreateJobApplicationWhenMessageIsBlank() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString("");
        String response = api.perform(
                        post(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldUpdateJobApplicationWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(user)
                        .jobPost(jobPostTest)
                        .message("This message is a test")
                        .build()
        );

        var applicationUpdated = JobApplicationRequestDTO.builder()
                .message("This is an updated message test")
                .build();

        String requestJSON = objectMapper.writeValueAsString(applicationUpdated);

        String response = api.perform(
                        put(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JobApplicationResponseDTO jobApplicationResponseDTO = objectMapper.readValue(response, JobApplicationResponseDTO.class);

        Assertions.assertEquals(application.getId(), jobApplicationResponseDTO.getId());
        Assertions.assertEquals(application.getApplicant().getId(), jobApplicationResponseDTO.getApplicant().getUserId());
        Assertions.assertEquals(application.getJobPost().getId(), jobApplicationResponseDTO.getJobPostId());
        Assertions.assertNotEquals(application.getMessage(), jobApplicationResponseDTO.getMessage());
        Assertions.assertEquals(applicationUpdated.getMessage(), jobApplicationResponseDTO.getMessage());
    }

    @Test
    public void isShouldNotUpdateJobApplicationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(user)
                        .jobPost(jobPostTest)
                        .message("This message is a test")
                        .build()
        );

        var applicationUpdated = JobApplicationRequestDTO.builder()
                .message("This is an updated message test")
                .build();

        String requestJSON = objectMapper.writeValueAsString(applicationUpdated);

        String response = api.perform(
                        put(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetListJobApplicationWhenAuthenticated() throws Exception {
        this.authenticateAs(userTest);
        var application1 = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(userTest)
                        .jobPost(jobPostTest)
                        .message("This message is a test 1")
                        .build()
        );

        var application2 = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(userTest)
                        .jobPost(jobPostTest)
                        .message("This message is a test 2")
                        .build()
        );

        var application3 = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(userTest)
                        .jobPost(jobPostTest)
                        .message("This message is a test 3")
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<JobApplicationResponseDTO> jobApplicationResponseDTOList = objectMapper.readValue(response, new TypeReference<List<JobApplicationResponseDTO>>() {
        });
        Assertions.assertNotNull(jobApplicationResponseDTOList);
        JobApplicationResponseDTO applicationTest1 = jobApplicationResponseDTOList.get(0);
        JobApplicationResponseDTO applicationTest2 = jobApplicationResponseDTOList.get(1);
        JobApplicationResponseDTO applicationTest3 = jobApplicationResponseDTOList.get(2);

        Assertions.assertEquals(application1.getId(), applicationTest1.getId());
        Assertions.assertEquals(application1.getApplicant().getId(), applicationTest1.getApplicant().getUserId());
        Assertions.assertEquals(application1.getJobPost().getId(), applicationTest1.getJobPostId());
        Assertions.assertEquals(application1.getMessage(), applicationTest1.getMessage());

        Assertions.assertEquals(application2.getId(), applicationTest2.getId());
        Assertions.assertEquals(application2.getApplicant().getId(), applicationTest2.getApplicant().getUserId());
        Assertions.assertEquals(application2.getJobPost().getId(), applicationTest2.getJobPostId());
        Assertions.assertEquals(application2.getMessage(), applicationTest2.getMessage());

        Assertions.assertEquals(application3.getId(), applicationTest3.getId());
        Assertions.assertEquals(application3.getApplicant().getId(), applicationTest3.getApplicant().getUserId());
        Assertions.assertEquals(application3.getJobPost().getId(), applicationTest3.getJobPostId());
        Assertions.assertEquals(application3.getMessage(), applicationTest3.getMessage());

    }

    @Test
    public void isShouldNotGetListJobApplicationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(user)
                        .jobPost(jobPostTest)
                        .message("This message is a test 1")
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications")
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldAllowUserToDeleteHisOwnJobApplicationWhenAuthenticated() throws Exception {
        this.authenticateAs(userTest);
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(userTest)
                        .jobPost(jobPostTest)
                        .message("This message is a test 1")
                        .build()
        );

        api.perform(
                        delete(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications/" + application.getId())
                )
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteHisOwnJobApplicationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(user)
                        .jobPost(jobPostTest)
                        .message("This message is a test 1")
                        .build()
        );

        api.perform(
                        delete(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications/" + application.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteJobApplicationMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        var application = jobApplicationRepository.save(
                JobApplication.builder()
                        .applicant(user)
                        .jobPost(jobPostTest)
                        .message("This message is a test 1")
                        .build()
        );

        String response = api.perform(
                        delete(API_JOB_APPLICATION + "/" + jobPostTest.getId() + "/applications/" + application.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse apiResponse = objectMapper.readValue(response, HttpResponse.class);

        Assertions.assertEquals("User does not have access to this job post.", apiResponse.getMessage());
    }
}

