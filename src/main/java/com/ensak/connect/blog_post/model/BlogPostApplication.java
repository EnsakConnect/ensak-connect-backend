package com.ensak.connect.blog_post.model;

import com.ensak.connect.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BlogPostApplication {

    @Id
    @GeneratedValue
    private  Integer Id;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private  BlogPost blogPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User applicant;

    private String message;

    @CreationTimestamp
    private  Date createdAt;

    @UpdateTimestamp
    private  Date updatedAt;





}
