package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.Education;
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
public class EducationRequestDTO {

    @NotBlank(message = "Field of study is required")
    @Size(max = 100)
    private String field;

    @NotBlank(message = "Degree is required")
    @Size(max = 100)
    private String degree;

    @NotBlank(message = "School name is required")
    @Size(max = 100)
    private String school;

    @NotNull(message = "Start date is required")
    private Date startDate;

    private Date endDate; // Optional, no validation

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    public static Education mapToEducation(EducationRequestDTO eDTO) {
        return Education.builder()
                .field(eDTO.field)
                .degree(eDTO.degree)
                .school(eDTO.school)
                .startDate(eDTO.startDate)
                .endDate(eDTO.endDate)
                .description(eDTO.description)
                .build();
    }
}
