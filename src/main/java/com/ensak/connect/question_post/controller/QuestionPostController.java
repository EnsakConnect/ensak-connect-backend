package com.ensak.connect.question_post.controller;

import com.ensak.connect.question_post.service.QuestionPostService;
import com.ensak.connect.question_post.dto.question.QuestionPostRequestDTO;
import com.ensak.connect.question_post.dto.question.QuestionPostResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/questions")
@RequiredArgsConstructor
public class QuestionPostController {
    private final QuestionPostService qnaService;

    @PostMapping
    public ResponseEntity<QuestionPostResponseDTO> create(
            @RequestBody @Valid QuestionPostRequestDTO request
    ) {
        return new ResponseEntity<>(qnaService.createQuestionPost(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionPostResponseDTO> show(@PathVariable Integer id){
        return ResponseEntity.ok(qnaService.getQuestionPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        qnaService.deleteQuestionPostById(id);
        return ResponseEntity.ok(null);
    }
}
