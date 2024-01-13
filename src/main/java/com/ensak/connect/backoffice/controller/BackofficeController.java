package com.ensak.connect.backoffice.controller;

import com.ensak.connect.auth.dto.UserResponseDTO;
import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.backoffice.dto.DashboardAllResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.backoffice.service.BackofficeService;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.service.BlogPostService;
import com.ensak.connect.blog_post.service.CommentPostService;
import com.ensak.connect.feed.dto.FeedResponceDTO;
import com.ensak.connect.feed.service.FeedService;
import com.ensak.connect.job_post.service.JobPostService;
import com.ensak.connect.question_post.service.QuestionPostService;
import com.ensak.connect.report.Report;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/back-offices")
@RequiredArgsConstructor
public class BackofficeController {

    private final BackofficeService backofficeService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<List<DashboardAllResponseDTO>> getCountAllDashboardCharts() {
        return ResponseEntity.ok(backofficeService.countDashboardCharts());
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> getUserPageWithSearchAndFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseEntity<>(backofficeService.getPageOfUsers(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/users/lock/{id}")
    public ResponseEntity<Void> patchIsLockedByUserId(@PathVariable Integer id) {
        userService.patchIsNotLockedById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/active/{id}")
    public ResponseEntity<Void> patchIsActiveByUserId(@PathVariable Integer id) {
        userService.patchIsActiveById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUserByUserId(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
