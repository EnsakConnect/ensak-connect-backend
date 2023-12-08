package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Experience;
import com.ensak.connect.profile.model.util.ContractType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Position title is required")
    @Size(max = 100)
    private String positionTitle;

    @NotNull(message = "Contract type is required")
    private ContractType contractType;

    @NotBlank(message = "Company name is required")
    @Size(max = 100)
    private String companyName;

    @NotBlank(message = "Location is required")
    @Size(max = 100)
    private String location;

    @NotNull(message = "Start date is required")
    private Date startDate;

    private Date endDate; // Optional, no validation

    @Size(max = 500, message = "Description must be less than 500 characters")
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
