package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Skill;
import com.ensak.connect.profile.model.util.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
