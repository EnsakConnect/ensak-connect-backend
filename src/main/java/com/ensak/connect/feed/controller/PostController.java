package com.ensak.connect.feed.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/myposts")
@RequiredArgsConstructor
public class PostController {
    private final AuthenticationService authenticationService;
    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<Page<FeedResponceDTO>> getUserPosts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(feedService.getUserPosts(pageRequest,userId), HttpStatus.OK);
    }
}
