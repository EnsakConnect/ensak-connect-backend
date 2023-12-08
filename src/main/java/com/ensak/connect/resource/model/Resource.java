package com.ensak.connect.resource.model;

import com.ensak.connect.resource.ResourceType;
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
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue
    private Integer id;

    private ResourceType type;

    private String filename;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private ResourceOwner owner;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
