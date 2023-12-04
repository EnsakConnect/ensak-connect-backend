package com.ensak.connect.integration.question_post;

import com.ensak.connect.config.api.ApiError;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.ensak.connect.question_post.dto.question.QuestionPostRequestDTO;
import com.ensak.connect.question_post.dto.question.QuestionPostResponseDTO;
import com.ensak.connect.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionPostIntegrationTest extends AuthenticatedBaseIntegrationTest {

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionPostRepository questionPostRepository;

    @Test
    public void itShouldCreateQNAPostWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        QuestionPostRequestDTO request = QuestionPostRequestDTO.builder()
                .question("Does this question exists?")
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);
        String response = api.perform(
            post("/api/v1/questions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJSON)
        )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    @Test
    public void itShouldNotCreateQNAPostWhenNotAuthenticated() throws Exception {
        QuestionPostRequestDTO request = QuestionPostRequestDTO.builder()
                .question("Does this question exists?")
                .build();
        String requestJSON = objectMapper.writeValueAsString(request);
        api.perform(
            post("/api/v1/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON)
        )
            .andExpect(status().isForbidden())
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    @Test
    public void isShouldGetQNAPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        QuestionPost post = questionPostRepository.save(
                QuestionPost.builder()
                        .question("Does this post exist ?")
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get("/api/v1/questions/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        QuestionPostResponseDTO postResponse = objectMapper.readValue(response, QuestionPostResponseDTO.class);

        Assertions.assertEquals(post.getId(), postResponse.getId());
        Assertions.assertEquals(post.getQuestion(), postResponse.getQuestion());
        Assertions.assertEquals(post.getAuthor().getId(), postResponse.getAuthor().getId());
    }

    @Test
    public void isShouldNotGetQNAPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        QuestionPost post = questionPostRepository.save(
                QuestionPost.builder()
                        .question("Does this post exist ?")
                        .author(user)
                        .build()
        );

        api.perform(
            get("/api/v1/questions/" + post.getId())
        )
            .andExpect(status().isForbidden())
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    @Test
    public void itShouldAllowUserToDeleteHisOwnQNAPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        QuestionPost post = questionPostRepository.save(
                QuestionPost.builder()
                        .question("Does this post exist ?")
                        .author(user)
                        .build()
        );

        api.perform(
                        delete("/api/v1/questions/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotAllowUserToDeleteQNAPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        QuestionPost post = questionPostRepository.save(
                QuestionPost.builder()
                        .question("Does this post exist ?")
                        .author(user)
                        .build()
        );

        api.perform(
                        delete("/api/v1/questions/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotDeleteQNAPostMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        QuestionPost post = questionPostRepository.save(
                QuestionPost.builder()
                        .question("Does this post exist ?")
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        delete("/api/v1/questions/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ApiError apiResponse = objectMapper.readValue(response, ApiError.class);

        Assertions.assertEquals("Cannot delete posts made by other users", apiResponse.getMessage());
    }

}
