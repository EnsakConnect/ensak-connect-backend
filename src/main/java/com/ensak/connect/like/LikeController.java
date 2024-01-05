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

        return new ResponseEntity<>(likeService.likeJobPost(id), HttpStatus.OK);
    }

    @GetMapping("/job-post/{id}/dislike")
    public ResponseEntity<String> dislikeJobPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(likeService.dislikeJobPost(id), HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/like")
    public ResponseEntity<String> likeQuestionPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(likeService.likeQuestionPost(id), HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/dislike")
    public ResponseEntity<String> dislikeQuestionPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(likeService.dislikeQuestionPost(id), HttpStatus.OK);
    }


    @GetMapping("/blog-post/{id}/like")
    public ResponseEntity<String> likeBlogPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(likeService.likeBlogPost(id), HttpStatus.OK);
    }

    @GetMapping("/blog-post/{id}/dislike")
    public ResponseEntity<String> dislikeBlogPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(likeService.dislikeBlogPost(id), HttpStatus.OK);
    }

}
