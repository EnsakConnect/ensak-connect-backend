package com.ensak.connect.qna_post;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.qna_post.dto.QNAPostRequestDTO;
import com.ensak.connect.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QNAPostService {
    private final QNAPostRepository qnaRepository;
    private final AuthenticationService authenticationService;

    public QNAPost getQNAPostById(Integer id) {
        return qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
    }

    public QNAPost createQNAPost(QNAPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return qnaRepository.save(
                QNAPost.builder()
                        .question(request.getQuestion())
                        .author(author)
                        .build()
        );
    }

    @Transactional
    public QNAPost updateQNAPostById(Integer id, QNAPostRequestDTO request){
        QNAPost post = qnaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
        post.setQuestion(request.getQuestion());
        return qnaRepository.save(post);
    }

    public void deleteQNAPostById(Integer id) {
        qnaRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Could not find qna post with id " + id + ".")
        );
        qnaRepository.deleteById(id);
    }
}
