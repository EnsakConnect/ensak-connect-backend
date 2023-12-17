package com.ensak.connect.integration.job_post;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.dto.JobPostResponseDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JobPostIntegrationTest extends AuthenticatedBaseIntegrationTest {



    public static final String API_JOB_POSTS = "/api/v1/job-posts";
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobPostRepository jobPostRepository;

    @AfterEach
    void tearDown() {
        jobPostRepository.deleteAll();
    }
    JobPostRequestDTO jobPostTest = JobPostRequestDTO.builder()
            .title("This is a job title test")
            .description("This is a job post description test")
            .companyName("This is the companyName test")
            .location("This is the location test")
            .companyType("This is the companyType test")
            .category("This is the category test")
            .tags(List.of(new String[]{"test1", "test2"}))
            .build();


    @Test
    public void itShouldCreateJobPostWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString(jobPostTest);
        String response = api.perform(
                        post(API_JOB_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }


    @Test
    public void itShouldNotCreateJobPostWhenNotAuthenticated() throws Exception {
        String requestJSON = objectMapper.writeValueAsString(jobPostTest);
        String response = api.perform(
                        post(API_JOB_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotCreateJobPostWhenTagsAreMoreThanAllowed() throws Exception {
        this.authenticateAsUser();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("tag " + i);
        }
        var request = jobPostTest;
        request.setTags(tags);
        String requestJSON = objectMapper.writeValueAsString(request);
        String response = api.perform(
                        post(API_JOB_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetJobPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_POSTS + "/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JobPostResponseDTO jobPostResponseDTO = objectMapper.readValue(response, JobPostResponseDTO.class);

        Assertions.assertEquals(post.getId(), jobPostResponseDTO.getId());
        Assertions.assertEquals(post.getTitle(), jobPostResponseDTO.getTitle());
        Assertions.assertEquals(post.getDescription(), jobPostResponseDTO.getDescription());
        Assertions.assertEquals(post.getCompanyName(), jobPostResponseDTO.getCompanyName());
        Assertions.assertEquals(post.getLocation(), jobPostResponseDTO.getLocation());
        Assertions.assertEquals(post.getCompanyType(), jobPostResponseDTO.getCompanyType());
        Assertions.assertEquals(post.getCategory(), jobPostResponseDTO.getCategory());
        Assertions.assertArrayEquals(post.getTags().toArray(), jobPostResponseDTO.getTags().toArray());
        Assertions.assertEquals(post.getAuthor().getId(), jobPostResponseDTO.getAuthor().getId());

    }

    @Test
    public void isShouldNotGetJobPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetListJobPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post1 = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle() + "1")
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );
        var post2 = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle() + "2")
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );
        var post3 = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle() + "3")
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );


        String response = api.perform(
                        get(API_JOB_POSTS)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<JobPostResponseDTO> jobPosts = objectMapper.readValue(response, new TypeReference<List<JobPostResponseDTO>>(){});
        Assertions.assertNotNull(jobPosts);
        JobPostResponseDTO postTest1 = jobPosts.get(0);
        JobPostResponseDTO postTest2 = jobPosts.get(1);
        JobPostResponseDTO postTest3 = jobPosts.get(2);

        Assertions.assertEquals(post1.getId(), postTest1.getId());
        Assertions.assertEquals(post1.getTitle(), postTest1.getTitle());
        Assertions.assertEquals(post1.getDescription(), postTest1.getDescription());
        Assertions.assertEquals(post1.getCompanyName(), postTest1.getCompanyName());
        Assertions.assertEquals(post1.getLocation(), postTest1.getLocation());
        Assertions.assertEquals(post1.getCompanyType(), postTest1.getCompanyType());
        Assertions.assertEquals(post1.getCategory(), postTest1.getCategory());
        Assertions.assertArrayEquals(post1.getTags().toArray(), postTest1.getTags().toArray());
        Assertions.assertEquals(post1.getAuthor().getId(), postTest1.getAuthor().getId());

        Assertions.assertEquals(post2.getId(), postTest2.getId());
        Assertions.assertEquals(post2.getTitle(), postTest2.getTitle());
        Assertions.assertEquals(post2.getDescription(), postTest2.getDescription());
        Assertions.assertEquals(post2.getCompanyName(), postTest2.getCompanyName());
        Assertions.assertEquals(post2.getLocation(), postTest2.getLocation());
        Assertions.assertEquals(post2.getCompanyType(), postTest2.getCompanyType());
        Assertions.assertEquals(post2.getCategory(), postTest2.getCategory());
        Assertions.assertArrayEquals(post2.getTags().toArray(), postTest2.getTags().toArray());
        Assertions.assertEquals(post2.getAuthor().getId(), postTest2.getAuthor().getId());

        Assertions.assertEquals(post3.getId(), postTest3.getId());
        Assertions.assertEquals(post3.getTitle(), postTest3.getTitle());
        Assertions.assertEquals(post3.getDescription(), postTest3.getDescription());
        Assertions.assertEquals(post3.getCompanyName(), postTest3.getCompanyName());
        Assertions.assertEquals(post3.getLocation(), postTest3.getLocation());
        Assertions.assertEquals(post3.getCompanyType(), postTest3.getCompanyType());
        Assertions.assertEquals(post3.getCategory(), postTest3.getCategory());
        Assertions.assertArrayEquals(post3.getTags().toArray(), postTest3.getTags().toArray());
        Assertions.assertEquals(post3.getAuthor().getId(), postTest3.getAuthor().getId());

    }

    @Test
    public void isShouldNotGetListJobPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_POSTS)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldUpdateJobPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        var postUpdated = JobPostRequestDTO.builder()
                .title(jobPostTest.getTitle() + " updated")
                .description(jobPostTest.getDescription() + " updated")
                .companyName(jobPostTest.getCompanyName() + " updated")
                .location(jobPostTest.getLocation() + " updated")
                .companyType(jobPostTest.getCompanyType() + " updated")
                .category(jobPostTest.getCategory() + " updated")
                .tags(jobPostTest.getTags())
                .build();

        String requestJSON = objectMapper.writeValueAsString(postUpdated);

        String response = api.perform(
                        put(API_JOB_POSTS + "/" + post.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JobPostResponseDTO jobPostResponseDTO = objectMapper.readValue(response, JobPostResponseDTO.class);

        Assertions.assertEquals(post.getId(), jobPostResponseDTO.getId());
        Assertions.assertNotEquals(post.getTitle(), jobPostResponseDTO.getTitle());
        Assertions.assertNotEquals(post.getDescription(), jobPostResponseDTO.getDescription());
        Assertions.assertNotEquals(post.getCompanyName(), jobPostResponseDTO.getCompanyName());
        Assertions.assertNotEquals(post.getLocation(), jobPostResponseDTO.getLocation());
        Assertions.assertNotEquals(post.getCompanyType(), jobPostResponseDTO.getCompanyType());
        Assertions.assertNotEquals(post.getCategory(), jobPostResponseDTO.getCategory());
        Assertions.assertNotEquals(post.getTags(), jobPostResponseDTO.getTags());
        Assertions.assertEquals(post.getAuthor().getId(), jobPostResponseDTO.getAuthor().getId());
        Assertions.assertEquals(postUpdated.getTitle(), jobPostResponseDTO.getTitle());
        Assertions.assertEquals(postUpdated.getDescription(), jobPostResponseDTO.getDescription());
        Assertions.assertEquals(postUpdated.getCompanyName(), jobPostResponseDTO.getCompanyName());
        Assertions.assertEquals(postUpdated.getLocation(), jobPostResponseDTO.getLocation());
        Assertions.assertEquals(postUpdated.getCompanyType(), jobPostResponseDTO.getCompanyType());
        Assertions.assertEquals(postUpdated.getCategory(), jobPostResponseDTO.getCategory());
        Assertions.assertEquals(postUpdated.getTags(), jobPostResponseDTO.getTags());

    }

    @Test
    public void isShouldNotUpdateJobPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        var postUpdated = JobPostRequestDTO.builder()
                .title(jobPostTest.getTitle() + " updated")
                .description(jobPostTest.getDescription() + " updated")
                .companyName(jobPostTest.getCompanyName() + " updated")
                .location(jobPostTest.getLocation() + " updated")
                .companyType(jobPostTest.getCompanyType() + " updated")
                .category(jobPostTest.getCategory() + " updated")
                .tags(jobPostTest.getTags())
                .build();

        String requestJSON = objectMapper.writeValueAsString(postUpdated);

        String response = api.perform(
                        put(API_JOB_POSTS + "/" + post.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldAllowUserToDeleteHisOwnJobPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_JOB_POSTS + "/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteHisOwnJobPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_JOB_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteJobPostMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        var post = jobPostRepository.save(
                JobPost.builder()
                        .title(jobPostTest.getTitle())
                        .description(jobPostTest.getDescription())
                        .companyName(jobPostTest.getCompanyName())
                        .location(jobPostTest.getLocation())
                        .companyType(jobPostTest.getCompanyType())
                        .category(jobPostTest.getCategory())
                        .tags(jobPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        delete(API_JOB_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse apiResponse = objectMapper.readValue(response, HttpResponse.class);

        Assertions.assertEquals("Cannot delete posts made by other users", apiResponse.getMessage());
    }
}
