package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.profile.models.util.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequestDTO {
    private String name;

    private Level level;

    public static Skill mapToSkill(SkillRequestDTO dto){
        return Skill.builder()
                .name(dto.name)
                .level(dto.level)
                .build();
    }
}
