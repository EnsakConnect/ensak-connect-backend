package com.ensak.connect.profile.models;

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
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String name;

    private String link;

    private String description;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}