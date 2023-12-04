package com.ensak.connect.profile;

import com.ensak.connect.exception.NotFoundException;
import com.ensak.connect.profile.dto.*;
import com.ensak.connect.profile.models.*;
import com.ensak.connect.profile.repositories.*;
import com.ensak.connect.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CertificationRepository certificationRepository;
    private  final SkillRepository skillRepository;
    private final LanguageRepository languageRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;
    private final ProjectRepository projectRepository;

    public void createEmptyProfile(User user, String fullName){
        Profile profile = Profile.builder().fullName(fullName).user(user).build();
        profileRepository.save(profile);
    }

    public Profile updateProfile(Integer user_id,ProfileRequestDTO pDTO){
        Profile profile = getUserProfileById(user_id);
        if(pDTO.getTitre() != null){
            profile.setTitre(pDTO.getTitre());
        }
        if(pDTO.getFullName() != null){
            profile.setFullName(pDTO.getFullName());
        }
        if(pDTO.getPhone() != null){
            profile.setPhone(pDTO.getPhone());
        }
        if(pDTO.getCity() != null){
            profile.setCity(pDTO.getCity());
        }
        if(pDTO.getAddress() != null){
            profile.setAddress(pDTO.getAddress());
        }
        return profileRepository.save(profile);
    }

    public Profile getUserProfileById(Integer user_id){
        return profileRepository.findProfileByUserId(user_id).orElseThrow(
                () -> new NotFoundException("Profile Not Found")
        );
    }

    // update banner, profile pic, CV

    public List<Certification> getCertifications(Integer user_id){
        return getUserProfileById(user_id).getCertificationList();
    }

    @Transactional
    public void addCertification(Integer user_id, CertificationRequestDTO cDTO){
        Profile profile = getUserProfileById(user_id);
        Certification certif = CertificationRequestDTO.mapToCertification(cDTO);
        certif.setProfile(profile);
        certificationRepository.save(certif);
    }

    @Transactional
    public void deleteCertification(Integer certifId){
        certificationRepository.findById(certifId).orElseThrow(
                () -> new NotFoundException("Certification Not Found")
        );
        certificationRepository.deleteById(certifId);
    }

    // Get skills for a user
    public List<Skill> getSkills(Integer userId) {
        return getUserProfileById(userId).getSkillList();
    }

    // Add a skill
    @Transactional
    public Skill addSkill(Integer userId, SkillRequestDTO sDTO) {
        Profile profile = getUserProfileById(userId);
        Skill skill = SkillRequestDTO.mapToSkill(sDTO);
        skill.setProfile(profile);
        System.out.println(skill.getName() +" " + skill.getLevel());
        return skillRepository.save(skill);
    }

    // Delete a skill
    @Transactional
    public void deleteSkill(Integer skillId) {
        skillRepository.findById(skillId).orElseThrow(
                () -> new NotFoundException("Skill Not Found")
        );
        skillRepository.deleteById(skillId);
    }

    // Get languages for a user
    public List<Language> getLanguages(Integer userId) {
        return getUserProfileById(userId).getLanguageList();
    }

    // Add a language
    @Transactional
    public void addLanguage(Integer userId, LanguageRequestDTO lDTO) {
        Profile profile = getUserProfileById(userId);
        Language language = LanguageRequestDTO.mapToLanguage(lDTO);
        language.setProfile(profile);
        languageRepository.save(language);
    }

    // Delete a language
    @Transactional
    public void deleteLanguage(Integer languageId) {
        languageRepository.findById(languageId).orElseThrow(
                () -> new NotFoundException("Language Not Found")
        );
        languageRepository.deleteById(languageId);
    }

    // Get education for a user
    public List<Education> getEducations(Integer userId) {
        return getUserProfileById(userId).getEducationList();
    }

    // Add an education
    @Transactional
    public void addEducation(Integer userId, EducationRequestDTO eDTO) {
        Profile profile = getUserProfileById(userId);
        Education education = EducationRequestDTO.mapToEducation(eDTO);
        education.setProfile(profile);
        educationRepository.save(education);
    }

    // Delete an education
    @Transactional
    public void deleteEducation(Integer educationId) {
        educationRepository.findById(educationId).orElseThrow(
                () -> new NotFoundException("Education Not Found")
        );
        educationRepository.deleteById(educationId);
    }

    // Get experiences for a user
    public List<Experience> getExperiences(Integer userId) {
        return getUserProfileById(userId).getExperienceList();
    }

    // Add an experience
    @Transactional
    public void addExperience(Integer userId, ExperienceRequestDTO eDTO) {
        Profile profile = getUserProfileById(userId);
        Experience experience = ExperienceRequestDTO.mapToExperience(eDTO);
        experience.setProfile(profile);
        experienceRepository.save(experience);
    }

    // Delete an experience
    @Transactional
    public void deleteExperience(Integer experienceId) {
        experienceRepository.findById(experienceId).orElseThrow(
                () -> new NotFoundException("Experience Not Found")
        );
        experienceRepository.deleteById(experienceId);
    }

    // Get projects for a user
    public List<Project> getProjects(Integer userId) {
        return getUserProfileById(userId).getProjectList();
    }

    // Add a project
    @Transactional
    public void addProject(Integer userId, ProjectRequestDTO pDTO) {
        Profile profile = getUserProfileById(userId);
        Project project = ProjectRequestDTO.mapToProject(pDTO);
        project.setProfile(profile);
        projectRepository.save(project);
    }

    // Delete a project
    @Transactional
    public void deleteProject(Integer projectId) {
        projectRepository.findById(projectId).orElseThrow(
                () -> new NotFoundException("Project Not Found")
        );
        projectRepository.deleteById(projectId);
    }

}
