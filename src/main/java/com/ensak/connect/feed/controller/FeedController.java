package com.ensak.connect.feed.controller;

import com.ensak.connect.feed.dto.FeedPageResponseDTO;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/feeds")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("?page={page}&size={size}")
    public ResponseEntity<Page<FeedResponceDTO>> getFeedPage(
            @PathVariable int page,
            @RequestParam int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseEntity<>(feedService.getPageOfFeed(pageRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<FeedResponceDTO>> getFeedPageWithSearchAndFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "ALL") String filter
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseEntity<>(feedService.getPageOfFeedWithSearchAndFilter(pageRequest, search, filter), HttpStatus.OK);
    }

}
