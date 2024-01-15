package com.ensak.connect.question_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.question_post.dto.answer.AnswerRequestDTO;
import com.ensak.connect.question_post.dto.answer.AnswerResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.AnswerRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.resource.ResourceRepository;
import com.ensak.connect.resource.ResourceService;
import com.ensak.connect.resource.model.Resource;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionPostService questionPostService;
    private final AuthenticationService authService;
    private final ResourceService resourceService;

    public List<AnswerResponseDTO> getAnswerByQuestionId(Integer questionPostId) {
        User author = authService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostService.getQuestionPostByIdForAnswers(questionPostId);
        List<Answer> answers = questionPost.getAnswers();
        if (answers != null){
            return AnswerResponseDTO.map(answers.stream().sorted(Comparator.comparing(Answer::getInteractionsCount)
                            .thenComparing(Answer::getUpdatedAt).reversed()).toList()
                    , author.getId());
        }else {
            return null;
        }

    }

    @SneakyThrows
    public AnswerResponseDTO createAnswerForQuestionPost(Integer questionPostId, AnswerRequestDTO request) {
        QuestionPost questionPost = questionPostService.getQuestionPostByIdForAnswers(questionPostId);
        User author = authService.getAuthenticatedUser();
        return AnswerResponseDTO.map(answerRepository.save(
                Answer.builder()
                        .content(request.getContent())
                        .questionPost(questionPost)
                        .author(author)
                        .interactionsCount(0)
                        .resources(resourceService.useResources(request.getResources(),author))
                        .build()
        ), author.getId());
    }

    @SneakyThrows
    public AnswerResponseDTO updateAnswerById(Integer questionPostId, Integer answerId, AnswerRequestDTO request) {
        User auth = authService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostService.getQuestionPostByIdForAnswers(questionPostId);
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new NotFoundException("Cannot find answer with the requested id.")
        );
        if(!questionPost.getId().equals(answer.getQuestionPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested answer.");
        }
        if(!answer.getAuthor().getId().equals(auth.getId())){
            throw new ForbiddenException("Cannot updated the requested answer.");
        }
        answer.setContent(request.getContent());
        answer.setResources(resourceService.updateUsedResource(
                answer.getResources().stream().map(Resource::getId).toList(),
                request.getResources(),
                auth
                )
        );
        return AnswerResponseDTO.map(answerRepository.save(answer), auth.getId());
    }

    @SneakyThrows
    public void deleteAnswerById(Integer questionPostId, Integer answerId) {
        QuestionPost questionPost = questionPostService.getQuestionPostByIdForAnswers(questionPostId);
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new NotFoundException("Cannot find answer with the requested id.")
        );
        if(!questionPost.getId().equals(answer.getQuestionPost().getId())) {
            throw new ForbiddenException("Cannot updated the requested answer.");
        }
        resourceService.unuseResources(
                answer.getResources().stream().map(Resource::getId).toList(),
                answer.getAuthor()
        );
        answerRepository.delete(answer);
    }

    public Answer getAnswerById(Integer answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new NotFoundException("Cannot find answer with the requested id.")
        );
        return answer;
    }
}
