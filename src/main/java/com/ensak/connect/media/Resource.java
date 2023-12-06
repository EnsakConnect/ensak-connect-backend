package com.ensak.connect.media;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "owner_id")
    private ResourceOwner owner;
}
