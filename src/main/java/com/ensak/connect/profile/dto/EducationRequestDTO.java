package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.models.Education;

import java.util.Date;

public class EducationRequestDTO {

    private String field;

    private String degree;

    private String school;

    private Date startDate;

    private Date endDate;

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
