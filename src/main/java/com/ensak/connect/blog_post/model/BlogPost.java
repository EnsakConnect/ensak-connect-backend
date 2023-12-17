package com.ensak.connect.blog_post.model;

import  com.ensak.connect.auth.model.User;
import com.ensak.connect.blog_post.model.CommentBlogPost;
import  com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {

    @Id
    @GeneratedValue
    private Integer id;

    private  String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User author;

    @JsonIgnore
    @OneToMany(mappedBy = "blogPost",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentBlogPost> comments;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BlogPostApplication>  blogPostApplications;


    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
