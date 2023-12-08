package com.ensak.connect.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Education {
    @Id
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String field;

    private String degree;

    private String school;

    private Date startDate;

    private Date endDate;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;
}
