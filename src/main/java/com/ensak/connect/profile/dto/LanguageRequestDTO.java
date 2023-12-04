package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Language;
import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.profile.models.util.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequestDTO {
    private String name;

    private Level level;

    public static Language mapToLanguage(LanguageRequestDTO dto){
        return Language.builder()
                .name(dto.name)
                .level(dto.level)
                .build();
    }
}
