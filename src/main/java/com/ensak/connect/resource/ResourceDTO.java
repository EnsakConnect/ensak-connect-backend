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

    private String filename;

    private ResourceType type;

    private Date createdAt;

    private Date updatedAt;

    public static ResourceDTO mapToDTO(Resource resource){
        return ResourceDTO.builder()
                .filename(resource.getFilename())
                .type(resource.getType())
                .createdAt(resource.getCreatedAt())
                .updatedAt(resource.getUpdatedAt())
                .build();
    }
}
