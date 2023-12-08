package com.ensak.connect.resource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ResourceOwner  {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    public Integer getId(){
        return id;
    }

    public abstract String getResourceOwnerType();

    public abstract String[] getAllowedExtensions();
}
