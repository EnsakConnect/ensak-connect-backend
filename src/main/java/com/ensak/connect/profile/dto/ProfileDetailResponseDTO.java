package com.ensak.connect.profile.dto;

import com.ensak.connect.profile.model.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProfileDetailResponseDTO {
    private Integer id;

    private String fullName;

    private String title;

    private Date createdAt;

    private Date updatedAt;

    private String profilePicture;

    private String banner;

    private String resume;

    private List<Skill> skillList;

    private List<Language> languageList;

    private List<Education> educationList;

    private List<Certification> certificationList;

    private List<Experience> experienceList;

    private List<Project> projectList;

    public static ProfileDetailResponseDTO mapToDTO(Profile profile){
        return ProfileDetailResponseDTO.builder()
                .id(profile.getId())
                .fullName(profile.getFullName())
                .title(profile.getTitle())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .skillList(profile.getSkillList())
                .languageList(profile.getLanguageList())
                .educationList(profile.getEducationList())
                .certificationList(profile.getCertificationList())
                .experienceList(profile.getExperienceList())
                .projectList(profile.getProjectList())
                .build();
    }

}
