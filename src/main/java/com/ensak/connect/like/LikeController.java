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
    public ResponseEntity<LikeResponseDTO> likeJobPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.likeJobPost(id)).build(), HttpStatus.OK);
    }

    @GetMapping("/job-post/{id}/dislike")
    public ResponseEntity<LikeResponseDTO> dislikeJobPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.dislikeJobPost(id)).build(), HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/like")
    public ResponseEntity<LikeResponseDTO> likeQuestionPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.likeQuestionPost(id)).build(), HttpStatus.OK);
    }

    @GetMapping("/question-post/{id}/dislike")
    public ResponseEntity<LikeResponseDTO> dislikeQuestionPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.dislikeQuestionPost(id)).build(), HttpStatus.OK);
    }


    @GetMapping("/blog-post/{id}/like")
    public ResponseEntity<LikeResponseDTO> likeBlogPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.likeBlogPost(id)).build(), HttpStatus.OK);
    }

    @GetMapping("/blog-post/{id}/dislike")
    public ResponseEntity<LikeResponseDTO> dislikeBlogPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(LikeResponseDTO.builder().message(likeService.dislikeBlogPost(id)).build(), HttpStatus.OK);
    }

}
