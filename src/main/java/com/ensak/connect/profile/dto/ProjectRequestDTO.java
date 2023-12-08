package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDTO {
    @NotBlank(message = "Project name is required")
    @Size(max = 100)
    private String name;

    //@NotBlank(message = "Project link is required")
    @Size(max = 200)
    @URL(message = "Invalid URL format")
    private String link;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    public static Project mapToProject(ProjectRequestDTO pDTO){
        return Project.builder()
                .name(pDTO.name)
                .link(pDTO.link)
                .description(pDTO.description)
                .build();
    }
}
