package com.ensak.connect.profile.models;

import com.ensak.connect.profile.models.util.ContractType;
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

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
