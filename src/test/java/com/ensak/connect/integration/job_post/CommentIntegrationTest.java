package com.ensak.connect.integration.job_post;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.blog_post.dto.CommentPostRequestDTO;
import com.ensak.connect.blog_post.dto.CommentPostResponseDTO;
import com.ensak.connect.blog_post.model.CommentPost;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.blog_post.repository.CommentPostRepository;
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
public class CommentIntegrationTest extends AuthenticatedBaseIntegrationTest {

    public static final String API_JOB_COMMENT = "/api/v1/job-posts";

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

    @AfterEach
    void tearDown() {
        jobPostRepository.deleteAll();
        commentPostRepository.deleteAll();
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

    CommentPostRequestDTO commentPostTest = CommentPostRequestDTO.builder()
            .content("This is a comment for a job application test")
            .build();


    @Test
    public void itShouldCreateCommentPostWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString(commentPostTest);
        String response = api.perform(
                        post(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }


    @Test
    public void itShouldNotCreateCommentPostWhenNotAuthenticated() throws Exception {
        String requestJSON = objectMapper.writeValueAsString(commentPostTest);
        String response = api.perform(
                        post(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotCreateCommentPostWhenMessageIsBlank() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString("");
        String response = api.perform(
                        post(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments")
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldUpdateCommentPostWhenAuthenticated() throws Exception {
        this.authenticateAs(userTest);
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test")
                        .build()
        );

        var commentUpdated = CommentPostRequestDTO.builder()
                .content("This is an updated comment test")
                .build();

        String requestJSON = objectMapper.writeValueAsString(commentUpdated);

        String response = api.perform(
                        put(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments/" + comment.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CommentPostResponseDTO commentPostResponseDTO = objectMapper.readValue(response, CommentPostResponseDTO.class);

        Assertions.assertEquals(comment.getId(), commentPostResponseDTO.getId());
        Assertions.assertEquals(comment.getAuthor().getId(), commentPostResponseDTO.getAuthor().getId());
        Assertions.assertNotEquals(comment.getContent(), commentPostResponseDTO.getContent());
    }

    @Test
    public void isShouldNotUpdateCommentPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(user)
                        .content("This is a comment test")
                        .build()
        );

        var commentUpdated = CommentPostRequestDTO.builder()
                .content("This is an updated comment test")
                .build();

        String requestJSON = objectMapper.writeValueAsString(commentUpdated);

        String response = api.perform(
                        put(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments/" + comment.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetListCommentPostWhenAuthenticated() throws Exception {
        this.authenticateAs(userTest);
        var comment1 = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test 1")
                        .build()
        );
        var comment2 = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test 2")
                        .build()
        );
        var comment3 = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test 3")
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<CommentPostResponseDTO> commentPostResponseDTOList = objectMapper.readValue(response, new TypeReference<List<CommentPostResponseDTO>>() {
        });
        Assertions.assertNotNull(commentPostResponseDTOList);
        CommentPostResponseDTO commentTest1 = commentPostResponseDTOList.get(0);
        CommentPostResponseDTO commentTest2 = commentPostResponseDTOList.get(1);
        CommentPostResponseDTO commentTest3 = commentPostResponseDTOList.get(2);

        Assertions.assertEquals(comment1.getId(), commentTest1.getId());
        Assertions.assertEquals(comment1.getAuthor().getId(), commentTest1.getAuthor().getId());
        Assertions.assertEquals(comment1.getContent(), commentTest1.getContent());

        Assertions.assertEquals(comment2.getId(), commentTest2.getId());
        Assertions.assertEquals(comment2.getAuthor().getId(), commentTest2.getAuthor().getId());
        Assertions.assertEquals(comment2.getContent(), commentTest2.getContent());

        Assertions.assertEquals(comment3.getId(), commentTest3.getId());
        Assertions.assertEquals(comment3.getAuthor().getId(), commentTest3.getAuthor().getId());
        Assertions.assertEquals(comment3.getContent(), commentTest3.getContent());
    }

    @Test
    public void isShouldNotGetListCommentPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(user)
                        .content("This is a comment test")
                        .build()
        );

        String response = api.perform(
                        get(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments")
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldAllowUserToDeleteHisOwnCommentPostWhenAuthenticated() throws Exception {
        this.authenticateAs(userTest);
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test")
                        .build()
        );

        api.perform(
                        delete(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments/" + comment.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteHisOwnCommentPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(userTest)
                        .content("This is a comment test")
                        .build()
        );

        api.perform(
                        delete(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments/" + comment.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteCommentPostMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        var comment = commentPostRepository.save(
                CommentPost.builder()
                        .jobPost(jobPostTest)
                        .author(user)
                        .content("This is a comment test")
                        .build()
        );

        String response = api.perform(
                        delete(API_JOB_COMMENT + "/" + jobPostTest.getId() + "/comments/" + comment.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse apiResponse = objectMapper.readValue(response, HttpResponse.class);

        Assertions.assertEquals("User does not have access to this comment.", apiResponse.getMessage());
    }
}
