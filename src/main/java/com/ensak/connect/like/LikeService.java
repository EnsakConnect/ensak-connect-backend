package com.ensak.connect.like;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
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
    private final BlogPostRepository blogPostRepository;
    private final AuthenticationService authenticationService;

    public String likeJobPost(Integer jobPostId) {
        User author = authenticationService.getAuthenticatedUser();
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + jobPostId + "."));
        List<Integer> likes = jobPost.getLikes();
        if (isLikedBySameAuthor(likes, author.getId()) == null) {
            likes.add(author.getId());
            jobPost.setLikes(likes);
            jobPostRepository.save(jobPost);
            return "Job post liked";
        }
        return "Job post already liked";
    }

    public String dislikeJobPost(Integer jobPostId) {
        User author = authenticationService.getAuthenticatedUser();
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(()-> new NotFoundException("Could not find job post with id " + jobPostId + "."));
//        Set<Like> likes = jobPost.getLikes();
//        Like like = isLikedBySameAuthor(likes, author);
//        if (like != null){
//            likes.remove(like);
//            jobPost.setLikes(likes);
//            jobPostRepository.save(jobPost);
//        }
        List<Integer> likes = jobPost.getLikes();
        Integer authorId = isLikedBySameAuthor(likes, author.getId());
        if (authorId != null) {
            likes.remove(authorId);
            jobPost.setLikes(likes);
            jobPostRepository.save(jobPost);
            return "Job post disliked";
        }
        return "Job post already disliked";
    }

    public String likeQuestionPost(Integer questionPostId) {
        User author = authenticationService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(()-> new NotFoundException("Could not find question post with id " + questionPostId + "."));
//        Set<Like> likes = questionPost.getLikes();
//        if (isLikedBySameAuthor(likes, author) == null){
//            likes.add(Like.builder()
//                    .author(author)
//                    .build());
//            questionPost.setLikes(likes);
//            questionPostRepository.save(questionPost);
//        }
        List<Integer> likes = questionPost.getLikes();
        if (isLikedBySameAuthor(likes, author.getId()) == null) {
            likes.add(author.getId());
            questionPost.setLikes(likes);
            questionPostRepository.save(questionPost);
            return "Question post liked";
        }
        return "Question post already liked";
    }

    public String dislikeQuestionPost(Integer questionPostId) {
        User author = authenticationService.getAuthenticatedUser();
        QuestionPost questionPost = questionPostRepository.findById(questionPostId)
                .orElseThrow(()-> new NotFoundException("Could not find question post with id " + questionPostId + "."));
//        Set<Like> likes = questionPost.getLikes();
//        Like like = isLikedBySameAuthor(likes, author);
//        if (like != null){
//            likes.remove(like);
//            questionPost.setLikes(likes);
//            questionPostRepository.save(questionPost);
//        }
        List<Integer> likes = questionPost.getLikes();
        Integer authorId = isLikedBySameAuthor(likes, author.getId());
        if (authorId != null) {
            likes.remove(authorId);
            questionPost.setLikes(likes);
            questionPostRepository.save(questionPost);
            return "Question post disliked";
        }
        return "Question post already disliked";
    }

    public String likeBlogPost(Integer blogPostId) {
        User author = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(()-> new NotFoundException("Could not find blog post with id " + blogPostId + "."));
        List<Integer> likes = blogPost.getLikes();
        if (isLikedBySameAuthor(likes, author.getId()) == null) {
            likes.add(author.getId());
            blogPost.setLikes(likes);
            blogPostRepository.save(blogPost);
            return "Blog post liked";
        }
        return "Blog post already liked";
    }

    public String dislikeBlogPost(Integer blogPostId) {
        User author = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(()-> new NotFoundException("Could not find blog post with id " + blogPostId + "."));
        List<Integer> likes = blogPost.getLikes();
        Integer authorId = isLikedBySameAuthor(likes, author.getId());
        if (authorId != null) {
            likes.remove(authorId);
            blogPost.setLikes(likes);
            blogPostRepository.save(blogPost);
            return "Blog post disliked";
        }
        return "Blog post already disliked";
    }

//    public Like isLikedBySameAuthor(Set<Like> likes, User author) {
//        for (Like like : likes) {
//            if (like.getAuthor().equals(author)){
//                return like;
//            }
//        }
//        return null;
//    }

    public Integer isLikedBySameAuthor(List<Integer> likes, Integer authorId) {
        for (Integer like : likes) {
            if (like.equals(authorId)){
                return authorId;
            }
        }
        return null;
    }
}
