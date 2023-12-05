package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.profile.models.util.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequestDTO {
    @NotBlank(message = "Skill name is required")
    @Size(max = 50)
    private String name;

    @NotNull(message = "Skill level is required")
    private Level level;

    public static Skill mapToSkill(SkillRequestDTO dto){
        return Skill.builder()
                .name(dto.name)
                .level(dto.level)
                .build();
    }
}
