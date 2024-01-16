package com.ensak.connect.interaction.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.interaction.model.Interaction;
import com.ensak.connect.interaction.repository.InteractionRepository;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.like.LikeRepository;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.AnswerRepository;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final AnswerRepository answerRepository;
    private final AuthenticationService authenticationService;

    public String interactAnswerUp(Integer answerId) {
        User author = authenticationService.getAuthenticatedUser();
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(()-> new NotFoundException("Could not find answer with id " + answerId + "."));
        Interaction interaction = interactionRepository.findByAuthorAndAnswer(author, answer);
        if (interaction != null && !interaction.getIsUp()){
            answer.setInteractionsCount(answer.getInteractionsCount() + 2);
            answerRepository.save(answer);
            interaction.setIsUp(true);
            interactionRepository.save(interaction);
            return "answer is up";
        } else if (interaction != null) {
            return "Answer already up";
        }else {
            answer.setInteractionsCount(answer.getInteractionsCount() + 1);
            answerRepository.save(answer);
            interactionRepository.save(Interaction.builder()
                            .author(author)
                            .answer(answer)
                            .isUp(true)
                    .build());
            return "answer is up";
        }
    }

    public String interactAnswerDown(Integer answerId) {
        User author = authenticationService.getAuthenticatedUser();
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(()-> new NotFoundException("Could not find answer with id " + answerId + "."));
        Interaction interaction = interactionRepository.findByAuthorAndAnswer(author, answer);
        if (interaction != null && interaction.getIsUp()){
            answer.setInteractionsCount(answer.getInteractionsCount() - 2);
            answerRepository.save(answer);
            interaction.setIsUp(false);
            interactionRepository.save(interaction);
            return "answer is down";
        } else if (interaction != null) {
            return "Answer already down";
        }else {
            answer.setInteractionsCount(answer.getInteractionsCount() - 1);
            answerRepository.save(answer);
            interactionRepository.save(Interaction.builder()
                    .author(author)
                    .answer(answer)
                    .isUp(false)
                    .build());
            return "answer is down";
        }
    }



}
