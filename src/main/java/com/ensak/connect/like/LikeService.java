package com.ensak.connect.like;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final JobPostRepository jobPostRepository;
    private final QuestionPostRepository questionPostRepository;
    private final AuthenticationService authenticationService;

    public void likeJobPost(Integer jobPostId) {
        User author = authenticationService.getAuthenticatedUser();
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + jobPostId + "."));
        Set<Like> likes = jobPost.getLikes();
        if (isLikedBySameAuthor(likes, author) == null){
            likes.add(Like.builder()
                    .author(author)
                    .build());
            jobPost.setLikes(likes);
            jobPostRepository.save(jobPost);
        }
    }

    public void dislikeJobPost(Integer jobPostId) {
        User author = authenticationService.getAuthenticatedUser();
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + jobPostId + "."));
        Set<Like> likes = jobPost.getLikes();
        Like like = isLikedBySameAuthor(likes, author);
        if (like != null){
            likes.remove(like);
            jobPost.setLikes(likes);
            jobPostRepository.save(jobPost);
        }
    }

    public void likeQuestionPost(Integer questionPostId) {
        User author = authenticationService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + questionPostId + "."));
        Set<Like> likes = questionPost.getLikes();
        if (isLikedBySameAuthor(likes, author) == null){
            likes.add(Like.builder()
                    .author(author)
                    .build());
            questionPost.setLikes(likes);
            questionPostRepository.save(questionPost);
        }
    }

    public void dislikeQuestionPost(Integer questionPostId) {
        User author = authenticationService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + questionPostId + "."));
        Set<Like> likes = questionPost.getLikes();
        Like like = isLikedBySameAuthor(likes, author);
        if (like != null){
            likes.remove(like);
            questionPost.setLikes(likes);
            questionPostRepository.save(questionPost);
        }
    }

    public Like isLikedBySameAuthor(Set<Like> likes, User author) {
        for (Like like : likes) {
            if (like.getAuthor().equals(author)){
                return like;
            }
        }
        return null;
    }
}
