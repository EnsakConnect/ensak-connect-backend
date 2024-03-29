package com.ensak.connect.question_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.question_post.dto.question.QuestionPostRequestDTO;
import com.ensak.connect.question_post.dto.question.QuestionPostResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import com.ensak.connect.auth.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionPostService {
    private final QuestionPostRepository qnaRepository;
    private final AuthenticationService authenticationService;

    public QuestionPostResponseDTO getQuestionPostById(Integer id) {
        User author = authenticationService.getAuthenticatedUser();
        var question = qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
        return QuestionPostResponseDTO.map(question, author.getId());
    }

    public QuestionPost getQuestionPostByIdForAnswers(Integer id) {

        return qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
    }

    public QuestionPostResponseDTO createQuestionPost(QuestionPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        QuestionPost post = QuestionPost.builder()
                .question(request.getQuestion())
                .author(author)
                .build();
        if(request.getTags() != null) {
            post.setTags(request.getTags());
        }
        return QuestionPostResponseDTO.map(qnaRepository.save(post), author.getId());
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
        if(!auth.getId().equals(post.getAuthor().getId()) && !auth.getRole().equals(Role.ROLE_ADMIN)){
            throw new ForbiddenException("Cannot delete posts made by other users");
        }
        qnaRepository.deleteById(id);
    }

    public List<QuestionPost> retrieveByTags(List<String> tags) {
        return qnaRepository.retrieveByTags(tags);
    }

    public DashboardResponseDTO getCountPostsMonthly () {
        return DashboardResponseDTO.mapO(qnaRepository.countByMonthOfCurrentYear());
    }
}
