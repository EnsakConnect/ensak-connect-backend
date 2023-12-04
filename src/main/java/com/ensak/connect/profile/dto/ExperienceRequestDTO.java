package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Education;
import com.ensak.connect.profile.models.Experience;
import com.ensak.connect.profile.models.util.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequestDTO {
    private String positionTitle;

    private ContractType contractType;

    private String companyName;

    private String location;

    private Date startDate;

    private Date endDate;

    private String description;
    public static Experience mapToExperience(ExperienceRequestDTO eDTO) {
        return Experience.builder()
                .positionTitle(eDTO.positionTitle)
                .contractType(eDTO.contractType)
                .companyName(eDTO.companyName)
                .location(eDTO.location)
                .startDate(eDTO.startDate)
                .endDate(eDTO.endDate)
                .description(eDTO.description)
                .build();
    }
}
