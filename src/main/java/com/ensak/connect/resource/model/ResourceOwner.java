package com.ensak.connect.resource.model;

import com.ensak.connect.resource.model.IResourceOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ResourceOwner implements IResourceOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Override
    public Integer getId(){
        return id;
    }
    @Override
    public abstract String getResourceOwnerType();

    @Override
    public abstract String[] getAllowedExtensions();
}
