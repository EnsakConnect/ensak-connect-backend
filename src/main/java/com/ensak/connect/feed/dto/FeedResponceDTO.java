package com.ensak.connect.feed.dto;


import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.dto.ProfileFeedResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
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

    private ProfileFeedResponseDTO author;

    private Integer commentsCount;

    private List<IntercationResponseDTO> interactions;

    private Integer likesCount;

    private List<String> tags;

    private String timePassed;

    public static FeedResponceDTO map(JobPost jobPost){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(jobPost.getId())
                .postType(jobPost.getCategory())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .resources(new ArrayList<>())
                .author(ProfileFeedResponseDTO.map(jobPost.getAuthor().getProfile(), jobPost.getAuthor().getProfileType()))
                .commentsCount(jobPost.getComments().size())
                .interactions(IntercationResponseDTO.mapComments(jobPost.getComments()))
                .likesCount(0)
                .tags(jobPost.getTags())
                .timePassed(prettyTime.format(jobPost.getUpdatedAt()))
                .build();
    }

    public static FeedResponceDTO map(QuestionPost questionPost){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(questionPost.getId())
                .postType("Q&A")
                .title(questionPost.getQuestion())
                .description(null)
                .resources(new ArrayList<>())
                .author(ProfileFeedResponseDTO.map(questionPost.getAuthor().getProfile(), questionPost.getAuthor().getProfileType()))
                .commentsCount(questionPost.getAnswers().size())
                .interactions(IntercationResponseDTO.mapAnswers(questionPost.getAnswers()))
                .likesCount(0)
                .tags(questionPost.getTags())
                .timePassed(prettyTime.format(questionPost.getUpdatedAt()))
                .build();
    }


}
