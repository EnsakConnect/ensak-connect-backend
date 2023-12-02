package com.ensak.connect.qna_post;

import com.ensak.connect.qna_post.dto.QNAPostRequestDTO;
import com.ensak.connect.qna_post.dto.QNAPostResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/question-and-answer")
@RequiredArgsConstructor
public class QNAController {
    private final QNAPostService qnaService;

    @PostMapping
    public ResponseEntity<QNAPostResponseDTO> create(
            @RequestBody @Valid QNAPostRequestDTO request
    ) {
        QNAPost post = qnaService.createQNAPost(request);

        return new ResponseEntity<>(QNAPostResponseDTO.map(post), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QNAPostResponseDTO> show(@PathVariable Integer id){
        QNAPost post = qnaService.getQNAPostById(id);
        return ResponseEntity.ok(QNAPostResponseDTO.map(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        qnaService.deleteQNAPostById(id);
        return ResponseEntity.ok(null);
    }
}
