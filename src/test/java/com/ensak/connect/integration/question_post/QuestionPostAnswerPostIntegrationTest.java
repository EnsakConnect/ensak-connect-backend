package com.ensak.connect.integration.question_post;

import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.question_post.dto.answer.AnswerRequestDTO;
import com.ensak.connect.question_post.dto.answer.AnswerResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.AnswerRepository;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.ensak.connect.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Transactional
    public void itShouldAddAnswerToQuestionPostWhenAuthenticated() throws Exception {
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
        response.andExpect(status().isCreated());
        String responseJson = response.andReturn().getResponse().getContentAsString();
        AnswerResponseDTO answerResponse = objectMapper.readValue(responseJson, AnswerResponseDTO.class);
        Answer newAnswer = answerRepository.findById(answerResponse.getId()).orElseThrow(
                () -> new NotFoundException("Answer was not created")
        );

        Assertions.assertEquals(answerAuthor.getId(), newAnswer.getAuthor().getId());
        Assertions.assertEquals("Here is the answer to the question:\nFollow TDD!", newAnswer.getContent());
        Assertions.assertEquals(questionPost.getId(), newAnswer.getQuestionPost().getId());
    }

    @Test
    public void itShouldReturnListOfAnswersForAPostWhenAuthenticated() throws Exception {
        User author = this.authenticateAsUser();
        String url = "/api/v1/questions/" + questionPost.getId() + "/answers";
        Integer answersCount = 3;
        for (int i = 0; i < answersCount; i++) {
            answerRepository.save(
                    Answer.builder()
                            .author(author)
                            .questionPost(questionPost)
                            .content("My test answer number " + i + ".")
                            .build()
            );
        }
        var getResponse = api.perform(
                get(url)
        );
        getResponse.andExpect(status().isOk());
        List<AnswerResponseDTO> answers = objectMapper.readValue(
                getResponse.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<AnswerResponseDTO>>() {}
        );
        Assertions.assertEquals(answersCount, answers.size());
    }

    @Test
    public void itShouldNotAddAnswerToQuestionPostWhenNotAuthenticated() throws Exception {
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
                put(url)
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
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        Answer newAnswer = answerRepository.findById(answer.getId()).orElseThrow(
                () -> new Exception("Answer was deleted or not created ?")
        );

        response.andExpect(status().isForbidden());
        Assertions.assertNotEquals("Answer content updated", newAnswer.getContent());
    }
}
