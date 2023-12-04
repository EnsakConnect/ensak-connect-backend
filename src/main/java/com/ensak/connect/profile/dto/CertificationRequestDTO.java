package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Certification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationRequestDTO {
    private String name;

    private String link;

    public static Certification mapToCertification(CertificationRequestDTO dto){
        return Certification.builder()
                .name(dto.name)
                .link(dto.link)
                .build();
    }
}
