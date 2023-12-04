package com.ensak.connect.question_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.question_post.dto.question.QuestionPostRequestDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.ensak.connect.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuestionPostService {
    private final QuestionPostRepository qnaRepository;
    private final AuthenticationService authenticationService;

    public QuestionPost getQuestionPostById(Integer id) {
        return qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
    }

    public QuestionPost createQuestionPost(QuestionPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return qnaRepository.save(
                QuestionPost.builder()
                        .question(request.getQuestion())
                        .author(author)
                        .build()
        );
    }

    @Transactional
    public QuestionPost updateQuestionPostById(Integer id, QuestionPostRequestDTO request){
        QuestionPost post = qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
        post.setQuestion(request.getQuestion());
        return qnaRepository.save(post);
    }

    @SneakyThrows
    public void deleteQuestionPostById(Integer id) {
        User auth = authenticationService.getAuthenticatedUser();
        QuestionPost post = qnaRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
        if(!auth.getId().equals(post.getAuthor().getId())){
            throw new ForbiddenException("Cannot delete posts made by other users");
        }
        qnaRepository.deleteById(id);
    }
}
