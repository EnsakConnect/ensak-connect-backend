package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDTO {
    private String name;

    private String link;

    private String description;

    public static Project mapToProject(ProjectRequestDTO pDTO){
        return Project.builder()
                .name(pDTO.name)
                .link(pDTO.link)
                .description(pDTO.description)
                .build();
    }
}
