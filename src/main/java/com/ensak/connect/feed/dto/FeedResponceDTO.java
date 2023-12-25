package com.ensak.connect.feed.dto;


import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedResponceDTO {

    private Integer id;

    private String postType;

    private String title;

    private String description;

    private List<String> resources;

    private ProfileResponseDTO author;

    private Integer commentsCount;

    private List<IntercationResponseDTO> interactions;

    private Integer likesCount;

    private List<String> tags;

    private Date updatedAt;

    private String timePassed;

    public static FeedResponceDTO map(JobPost jobPost){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(jobPost.getId())
                .postType(jobPost.getCategory().toUpperCase())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .resources(new ArrayList<>())
                .author(ProfileResponseDTO.mapToDTO(jobPost.getAuthor().getProfile()))
                .commentsCount(jobPost.getComments().size())
                .interactions(IntercationResponseDTO.mapComments(jobPost.getComments()))
                .likesCount(0)
                .updatedAt(jobPost.getUpdatedAt())
                .tags(jobPost.getTags())
                .timePassed(prettyTime.format(jobPost.getUpdatedAt()))
                .build();
    }

    public static List<FeedResponceDTO> mapJobPosts(List<JobPost> jobPosts) {
        if (jobPosts == null) {
            return null;
        }
        List<FeedResponceDTO> result = new ArrayList<FeedResponceDTO>(jobPosts.size());
        for (JobPost jobPost: jobPosts){
            result.add(map(jobPost));
        }
        return result;
    }

    public static FeedResponceDTO map(QuestionPost questionPost){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(questionPost.getId())
                .postType("Q&A")
                .title(questionPost.getQuestion())
                .description(null)
                .resources(new ArrayList<>())
                .author(ProfileResponseDTO.mapToDTO(questionPost.getAuthor().getProfile()))
                .commentsCount(questionPost.getAnswers().size())
                .interactions(IntercationResponseDTO.mapAnswers(questionPost.getAnswers()))
                .likesCount(0)
                .updatedAt(questionPost.getUpdatedAt())
                .tags(questionPost.getTags())
                .timePassed(prettyTime.format(questionPost.getUpdatedAt()))
                .build();
    }

    public static List<FeedResponceDTO> mapQuestionPosts(List<QuestionPost> questionPosts) {
        if (questionPosts == null) {
            return null;
        }
        List<FeedResponceDTO> result = new ArrayList<FeedResponceDTO>(questionPosts.size());
        for (QuestionPost questionPost: questionPosts){
            result.add(map(questionPost));
        }
        return result;
    }
}
