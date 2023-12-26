package com.ensak.connect.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/job-post/{id}/like")
    public ResponseEntity<String> likeJobPost (
            @PathVariable Integer id
    ) {
        likeService.likeJobPost(id);
        return new ResponseEntity<>("Job post liked", HttpStatus.OK);
    }

    @GetMapping("/job-post/{id}/dislike")
    public ResponseEntity<String> dislikeJobPost (
            @PathVariable Integer id
    ) {
        likeService.dislikeJobPost(id);
        return new ResponseEntity<>("Job post disliked", HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/like")
    public ResponseEntity<String> likeQuestionPost (
            @PathVariable Integer id
    ) {
        likeService.likeQuestionPost(id);
        return new ResponseEntity<>("Question post liked", HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/dislike")
    public ResponseEntity<String> dislikeQuestionPost (
            @PathVariable Integer id
    ) {
        likeService.dislikeQuestionPost(id);
        return new ResponseEntity<>("Question post disliked", HttpStatus.OK);
    }


}
