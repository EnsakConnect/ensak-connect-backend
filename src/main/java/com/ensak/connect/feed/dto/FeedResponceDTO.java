package com.ensak.connect.feed.dto;


import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.resource.model.Resource;
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

    private CompanyFeedDTO company;

    private List<String> resources;

    private ProfileResponseDTO author;

    private Integer commentsCount;

    private Integer likesCount;

    private Boolean isLiked;

    private List<String> tags;

    private Date updatedAt;

    private String timePassed;

    public static FeedResponceDTO map(JobPost jobPost, Integer authorId){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(jobPost.getId())
                .postType(jobPost.getCategory().toUpperCase())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .author(ProfileResponseDTO.mapToDTO(jobPost.getAuthor().getProfile()))
                .commentsCount(jobPost.getJobApplications().size())
                .company(CompanyFeedDTO.builder()
                        .logo(null)
                        .companyName(jobPost.getCompanyName())
                        .location(jobPost.getLocation())
                        .build()
                )
                .likesCount(jobPost.getLikes().size())
                .isLiked(jobPost.getLikes().contains(authorId))
                .resources(
                        jobPost.getResources() != null
                                ? jobPost.getResources().stream().map(Resource::getFilename).toList()
                                : null
                )
                .updatedAt(jobPost.getUpdatedAt())
                .tags(jobPost.getTags())
                .timePassed(prettyTime.format(jobPost.getUpdatedAt()))
                .build();
    }

    public static List<FeedResponceDTO> mapJobPosts(List<JobPost> jobPosts, Integer authId) {
        if (jobPosts == null) {
            return null;
        }
        List<FeedResponceDTO> result = new ArrayList<FeedResponceDTO>(jobPosts.size());
        for (JobPost jobPost: jobPosts){
            result.add(map(jobPost, authId));
        }
        return result;
    }

    public static FeedResponceDTO map(BlogPost blogPost, Integer authorId){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(blogPost.getId())
                .postType("BlogPost")
                .title(null)
                .description(blogPost.getContent())
                .author(ProfileResponseDTO.mapToDTO(blogPost.getAuthor().getProfile()))
                .commentsCount(blogPost.getComments().size())
                .likesCount(blogPost.getLikes().size())
                .isLiked(blogPost.getLikes().contains(authorId))
                .resources(
                        blogPost.getResources() != null
                                ? blogPost.getResources().stream().map(Resource::getFilename).toList()
                                : null
                )
                .updatedAt(blogPost.getUpdatedAt())
                .tags(blogPost.getTags())
                .timePassed(prettyTime.format(blogPost.getUpdatedAt()))
                .build();
    }

    public static List<FeedResponceDTO> mapBlogPosts(List<BlogPost> blogPosts, Integer authId) {
        if (blogPosts == null) {
            return null;
        }
        List<FeedResponceDTO> result = new ArrayList<FeedResponceDTO>(blogPosts.size());
        for (BlogPost blogPost: blogPosts){
            result.add(map(blogPost, authId));
        }
        return result;
    }

    public static FeedResponceDTO map(QuestionPost questionPost, Integer authId){
        PrettyTime prettyTime = new PrettyTime();
        return FeedResponceDTO.builder()
                .id(questionPost.getId())
                .postType("Q&A")
                .title(questionPost.getQuestion())
                .description(null)
                .company(null)
                .resources(new ArrayList<>())
                .author(ProfileResponseDTO.mapToDTO(questionPost.getAuthor().getProfile()))
                .commentsCount(questionPost.getAnswers().size())
                .likesCount(questionPost.getLikes().size())
                .isLiked(questionPost.getLikes().contains(authId))
                .updatedAt(questionPost.getUpdatedAt())
                .tags(questionPost.getTags())
                .timePassed(prettyTime.format(questionPost.getUpdatedAt()))
                .build();
    }

    public static List<FeedResponceDTO> mapQuestionPosts(List<QuestionPost> questionPosts, Integer authId) {
        if (questionPosts == null) {
            return null;
        }
        List<FeedResponceDTO> result = new ArrayList<FeedResponceDTO>(questionPosts.size());
        for (QuestionPost questionPost: questionPosts){
            result.add(map(questionPost, authId));
        }
        return result;
    }
}
