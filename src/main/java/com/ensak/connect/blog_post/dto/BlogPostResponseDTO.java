package com.ensak.connect.blog_post.dto;

import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.resource.ResourceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostResponseDTO {

    private Integer id;
    private String content;
    private List<String> tags;
    private ProfileResponseDTO author;
    private List<String> resources;
    private Boolean isLiked;
    private Integer commentsCount;
    private Integer likesCount;
    private Date createdAt;
    private Date updatedAt;

    public static BlogPostResponseDTO map(BlogPost blogPost, Integer authorId) {
        return BlogPostResponseDTO.builder()
                .id(blogPost.getId())
                .content(blogPost.getContent())
                .tags(blogPost.getTags())
                .isLiked(blogPost.getLikes() != null && blogPost.getLikes().contains(authorId))
                .likesCount(blogPost.getLikes() != null ? blogPost.getLikes().size() : 0)
                .commentsCount(blogPost.getComments() != null ? blogPost.getComments().size() : 0)
                .author(ProfileResponseDTO.mapToDTO(blogPost.getAuthor()))
                .resources(ResourceDTO.toString(blogPost.getResources()))
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .build();
    }

    public static List<BlogPostResponseDTO> map(List<BlogPost> blogPosts, Integer authorId) {
        if (blogPosts == null) {
            return null;
        }

        List<BlogPostResponseDTO> list = new ArrayList<BlogPostResponseDTO>(blogPosts.size());
        for (BlogPost blogPost : blogPosts) {
            list.add( map(blogPost, authorId));
        }

        return list;
    }
}
