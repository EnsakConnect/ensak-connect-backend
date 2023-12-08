package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Certification;
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
public class CertificationRequestDTO {
    @NotBlank(message = "Certification name is required")
    @Size(min = 3, max = 100, message = "Certification name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Certification link is required")
    @Size(max = 200, message = "Certification link must be less than 200 characters")
    @URL(message = "Invalid URL format for certification link")
    private String link;

    public static Certification mapToCertification(CertificationRequestDTO dto){
        return Certification.builder()
                .name(dto.name)
                .link(dto.link)
                .build();
    }
}
