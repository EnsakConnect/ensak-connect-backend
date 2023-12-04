package com.ensak.connect.integration.qna_post;

import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.question_post.dto.answer.AnswerRequestDTO;
import com.ensak.connect.question_post.dto.answer.AnswerResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.AnswerRepository;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.ensak.connect.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionPostAnswerPostIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestionPostRepository questionPostRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private QuestionPost questionPost;

    @BeforeEach
    public void setup() {
        questionPost = questionPostRepository.save(
            QuestionPost.builder()
                .author(this.createDummyUser())
                .question("Does this post have answers ?")
                .build()
        );
    }

    @Test
    public void itShouldAddAnswerToQNAPostWhenAuthenticated() throws Exception {
        User answerAuthor = this.authenticateAsUser();
        String url = "/api/v1/questions/" + questionPost.getId() + "/answers";

        String answerJson = objectMapper.writeValueAsString(
                AnswerRequestDTO.builder()
                        .content("Here is the answer to the question:\nFollow TDD!")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        String responseJson = response.andReturn().getResponse().getContentAsString();
        AnswerResponseDTO answerResponse = objectMapper.readValue(responseJson, AnswerResponseDTO.class);
        QuestionPost newPost = questionPostRepository.findById(questionPost.getId()).orElseThrow(
                () -> new NotFoundException("post not found")
        );
        Answer newAnswer = answerRepository.findById(answerResponse.getId()).orElseThrow(
                () -> new NotFoundException("Answer was not created")
        );

        response.andExpect(status().isCreated());
        Assertions.assertEquals(answerAuthor.getId(), newAnswer.getAuthor().getId());
        Assertions.assertEquals(questionPost.getId(), newAnswer.getQuestionPost().getId());
        Assertions.assertEquals(1, newPost.getAnswers().size());
    }

    @Test
    public void itShouldNotAddAnswerToQNAPostWhenNotAuthenticated() throws Exception {
        String url = "/api/v1/questions/" + questionPost.getId() + "/answers";
        String answerJson = objectMapper.writeValueAsString(
                AnswerRequestDTO.builder()
                        .content("Here is the answer to the question:\nFollow TDD!")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        response.andExpect(status().isForbidden());
    }

    @Test
    public void itShouldAllowUserToEditHisOwnAnswer() throws Exception {
        User answerAuthor = this.authenticateAsUser();
        Answer answer = answerRepository.save(
                Answer.builder()
                        .content("My test answer")
                        .author(answerAuthor)
                        .questionPost(questionPost)
                        .build()
        );
        String url = "/api/v1/questions/" + questionPost.getId() + "/answers/" + answer.getId();

        String answerJson = objectMapper.writeValueAsString(
                AnswerRequestDTO.builder()
                        .content("Answer content updated")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        String responseJson = response.andReturn().getResponse().getContentAsString();
        AnswerResponseDTO answerResponse = objectMapper.readValue(responseJson, AnswerResponseDTO.class);
        Answer newAnswer = answerRepository.findById(answerResponse.getId()).orElseThrow(
                () -> new Exception("Answer was deleted or not created ?")
        );

        response.andExpect(status().isOk());
        Assertions.assertEquals("Answer content updated", newAnswer.getContent());
    }

    @Test
    public void itShouldNotAllowUserToEditAnswerMadeByOtherUsers() throws Exception {
        this.authenticateAsUser();
        Answer answer = answerRepository.save(
                Answer.builder()
                        .content("My test answer")
                        .author(this.createDummyStudent())
                        .questionPost(questionPost)
                        .build()
        );
        String url = "/api/v1/questions/" + questionPost.getId() + "/answers/" + answer.getId();

        String answerJson = objectMapper.writeValueAsString(
                AnswerRequestDTO.builder()
                        .content("Answer content updated")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        Answer newAnswer = answerRepository.findById(answer.getId()).orElseThrow(
                () -> new Exception("Answer was deleted or not created ?")
        );

        response.andExpect(status().isForbidden());
        Assertions.assertNotEquals("Answer content updated", newAnswer.getContent());
        Assertions.assertNotEquals("My test answer", newAnswer.getContent());
    }
}
