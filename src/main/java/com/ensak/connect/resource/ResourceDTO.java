package com.ensak.connect.resource;

import com.ensak.connect.resource.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {

    private Integer id;

    private String filename;

    private Date createdAt;

    private Date updatedAt;

    public static ResourceDTO mapToDTO(Resource resource){
        return ResourceDTO.builder()
                .id(resource.getId())
                .filename(resource.getFilename())
                .createdAt(resource.getCreatedAt())
                .updatedAt(resource.getUpdatedAt())
                .build();
    }
}
