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
public class Experience {
    @Id
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String positionTitle;

    private ContractType contractType;

    private String companyName;

    private String location;

    private Date startDate;

    private Date endDate;

    private String description;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
