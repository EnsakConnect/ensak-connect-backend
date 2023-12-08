package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Language;
import com.ensak.connect.profile.model.util.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequestDTO {

    @NotBlank(message = "Language name is required")
    @Size(max = 50)
    private String name;

    @NotNull(message = "Language level is required")
    private Level level;

    public static Language mapToLanguage(LanguageRequestDTO dto){
        return Language.builder()
                .name(dto.name)
                .level(dto.level)
                .build();
    }
}
