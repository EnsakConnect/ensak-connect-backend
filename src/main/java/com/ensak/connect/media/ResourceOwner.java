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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ResourceOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    public abstract String getResourceOwnerType();

    public abstract String[] getAllowedExtensions();
}
