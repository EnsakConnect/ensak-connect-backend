package com.ensak.connect.job_post.model;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.blog_post.model.CommentPost;
import com.ensak.connect.resource.model.Resource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String companyName;

    private String location;

    private String companyType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private String category;

    private String companyLogo;

    @ElementCollection
    private List<Integer> likes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> jobApplications;

    @OneToMany
    @JoinTable(
            name = "job_post_resources",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private List<Resource> resources;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
