package com.ensak.connect.question_post.controller;


import com.ensak.connect.question_post.dto.answer.AnswerRequestDTO;
import com.ensak.connect.question_post.dto.answer.AnswerResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import com.ensak.connect.question_post.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/questions/{question_id}/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping
    public  ResponseEntity<List<AnswerResponseDTO>> getQuestionAnswers(
            @PathVariable Integer question_id
    ) {
        return new ResponseEntity<>(
                answerService.getAnswerByQuestionId(question_id).stream().map(
                        AnswerResponseDTO::map
                ).toList(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<AnswerResponseDTO> create(
            @PathVariable Integer question_id,
            @RequestBody @Valid AnswerRequestDTO request
    ) {
        Answer answer = answerService.createAnswerForQuestionPost(question_id, request);
        return new ResponseEntity<>(AnswerResponseDTO.map(answer), HttpStatus.CREATED);
    }

    @PutMapping("/{answer_id}")
    public ResponseEntity<AnswerResponseDTO> update(
            @PathVariable Integer question_id,
            @PathVariable Integer answer_id,
            @RequestBody @Valid AnswerRequestDTO request
    ) {
        Answer answer = answerService.updateAnswerById(question_id, answer_id, request);
        return new ResponseEntity<>(AnswerResponseDTO.map(answer), HttpStatus.OK);
    }

    @DeleteMapping("/{answer_id}")
    public ResponseEntity<Object> delete(
            @PathVariable Integer question_id,
            @PathVariable Integer answer_id
    ) {
        answerService.deleteAnswerById(question_id, answer_id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
