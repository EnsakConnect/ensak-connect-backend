package com.ensak.connect.integration.qna_post;

import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.qna_post.QNAPost;
import com.ensak.connect.qna_post.QNAPostRepository;
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
public class QNAPostAnswerPostIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QNAPostRepository qnaPostRepository;
    @Autowired
    private QNAPostANswerRepository qnaPostAnswerRepository;

    private QNAPost qnaPost;

    @BeforeEach
    public void setup() {
        qnaPost = qnaPostRepository.save(
            QNAPost.builder()
                .author(this.createDummyUser())
                .question("Does this post have answers ?")
                .build()
        );
    }

    @Test
    public void itShouldAddAnswerToQNAPostWhenAuthenticated() throws Exception {
        User answerAuthor = this.authenticateAsUser();
        String url = "/api/v1/questions/" + qnaPost.getId() + "/answers";

        String answerJson = objectMapper.writeValueAsString(
                QNAPostAnswerRequestDTO.builder()
                        .content("Here is the answer to the question:\nFollow TDD!")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        String responseJson = response.andReturn().getResponse().getContentAsString();
        QNAPostAnswer answer = objectMapper.readValue(responseJson. QnaPostAnswerResponseDTO.class);
        QNAPost newPost = qnaPostRepository.findById(qnaPost.getId()).orElseThrow(
                () -> new NotFoundException("post not found")
        );

        response.andExpect(status().isCreated());
        Assertions.assertEquals(answerAuthor.getId(), answer.getAuthor().getId());
        Assertions.assertEquals(qnaPost.getId(), answer.getQnaPost().getId());
        Assertions.assertEquals(1,newPost.getAnswers().size());
    }

    @Test
    public void itShouldNotAddAnswerToQNAPostWhenNotAuthenticated() throws Exception {
        String url = "/api/v1/questions/" + qnaPost.getId() + "/answers";
        String answerJson = objectMapper.writeValueAsString(
                QNAPostAnswerRequestDTO.builder()
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
        QNAPostAnswer answer = qnaPostAnswerRepository.save(
                QNAPostAnswer.builder()
                        .content("My test answer")
                        .author(answerAuthor)
                        .qnaPost(qnaPost)
                        .build()
        );
        String url = "/api/v1/questions/" + qnaPost.getId() + "/answers/" + answer.getId();

        String answerJson = objectMapper.writeValueAsString(
                QNAPostAnswerRequestDTO.builder()
                        .content("Answer content updated")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        String responseJson = response.andReturn().getResponse().getContentAsString();
        QNAPostAnswer newAnswer = qnaPostAnswerRepository.findById(answerResponse.getId()).orElseThrow(
                () -> new Exception("Answer was deleted or not created ?")
        );

        response.andExpect(status().isOk());
        Assertions.assertEquals("Answer content updated", newAnswer.getContent());
    }

    @Test
    public void itShouldNotAllowUserToEditAnswerMadeByOtherUsers() throws Exception {
        this.authenticateAsUser();
        QNAPostAnswer answer = qnaPostAnswerRepository.save(
                QNAPostAnswer.builder()
                        .content("My test answer")
                        .author(this.createDummyStudent())
                        .qnaPost(qnaPost)
                        .build()
        );
        String url = "/api/v1/questions/" + qnaPost.getId() + "/answers/" + answer.getId();

        String answerJson = objectMapper.writeValueAsString(
                QNAPostAnswerRequestDTO.builder()
                        .content("Answer content updated")
                        .build()
        );
        var response = api.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerJson)
        );
        QNAPostAnswer newAnswer = qnaPostAnswerRepository.findById(answerResponse.getId()).orElseThrow(
                () -> new Exception("Answer was deleted or not created ?")
        );

        response.andExpect(status().isForbidden());
        Assertions.assertNotEquals("Answer content updated", newAnswer.getContent());
        Assertions.assertNotEquals("My test answer", newAnswer.getContent());
    }
}
