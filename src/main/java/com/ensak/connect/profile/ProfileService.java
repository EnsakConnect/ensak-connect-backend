package com.ensak.connect.profile;

import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.profile.dto.*;
import com.ensak.connect.profile.model.*;
import com.ensak.connect.profile.repository.*;
import com.ensak.connect.resource.ResourceService;
import com.ensak.connect.resource.ResourceType;
import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.resource.model.ResourceOwner;
import com.ensak.connect.auth.enums.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final ResourceService resourceService;

    public void createEmptyProfile(User user, String fullName){
        Profile profile = Profile.builder().fullName(fullName).user(user).build();
        profileRepository.save(profile);
    }

    public Profile updateProfile(Integer user_id,ProfileRequestDTO pDTO){
        Profile profile = getUserProfileById(user_id);
        if(pDTO.getTitle() != null){
            profile.setTitle(pDTO.getTitle());
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

    public Resource handleProfileResourceUpload(User user, ResourceType resume, MultipartFile file) {
        Profile profile = getUserProfileById(user.getId());

        List<Resource> resources = resourceService.getAllOwnerResource(profile, resume);
        Resource resource;
        if (resources == null || resources.isEmpty()) {
            resource = resourceService.createResource(profile, resume, file);
        } else {
            resource = resources.get(0);
            resource = resourceService.updateResource(resource, resume, file);
        }
        return resource;
    }

    public Profile getUserProfileById(Integer userId){
        return profileRepository.findProfileByUserId(userId).orElseThrow(
                () -> new NotFoundException("Profile Not Found")
        );
    }

    public ProfileDetailResponseDTO getDetailedProfile(Integer userId){
        Profile profile = profileRepository.findProfileByUserId(userId).orElseThrow(
                () -> new NotFoundException("Profile Not Found")
        );

        ProfileDetailResponseDTO responseDTO = ProfileDetailResponseDTO.mapToDTO(profile);
        Resource profilePicture = getProfilePicture(profile);
        if(profilePicture != null){
            responseDTO.setProfilePicture(profilePicture.getFilename());
        }
        Resource banner = getBanner(profile);
        if(profilePicture != null){
            responseDTO.setBanner(banner.getFilename());
        }
        Resource resume = getResume(profile);
        if(profilePicture != null){
            responseDTO.setResume(resume.getFilename());
        }

        return responseDTO;
    }

    public Resource getProfilePicture(ResourceOwner owner) {
        List<Resource> resources = resourceService.getAllOwnerResource(owner, ResourceType.ProfilePicture);
        return resources.isEmpty() ? null : resources.get(0);
    }
    public Resource getBanner(ResourceOwner owner) {
        List<Resource> resources = resourceService.getAllOwnerResource(owner, ResourceType.Banner);
        return resources.isEmpty() ? null : resources.get(0);
    }
    public Resource getResume(ResourceOwner owner) {
        List<Resource> resources = resourceService.getAllOwnerResource(owner, ResourceType.Resume);
        return resources.isEmpty() ? null : resources.get(0);
    }
    public ProfileResponseDTO getSummaryProfile(Integer userId){
        Profile profile = profileRepository.findProfileByUserId(userId).orElseThrow(
                () -> new NotFoundException("Profile Not Found")
        );

        ProfileResponseDTO responseDTO = ProfileResponseDTO.mapToDTO(profile);
        Resource profilePicture = getProfilePicture(profile);
        if(profilePicture != null){
            responseDTO.setProfilePicture(profilePicture.getFilename());
        }
        return responseDTO;
    }


    public List<Certification> getCertifications(Integer userId) {
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Certification>> certifications = certificationRepository.findAllByProfileId(profileId);
        return certifications.orElse(Collections.emptyList());
    }
    
    public Certification getCertification(Integer userId, Integer certifId){
        Integer profileId = getUserProfileById(userId).getId();
        return certificationRepository.findByProfileIdAndId(profileId,certifId).orElseThrow(
                () -> new NotFoundException("Certification Not Found")
        );
    }

    @Transactional
    public Certification updateCertification(Integer userId,Integer certifId, CertificationRequestDTO cDTO){
        Certification certification=  getCertification(userId,certifId);
        Certification update = CertificationRequestDTO.mapToCertification(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return certificationRepository.save(update);
    }
    
    @Transactional
    public Certification addCertification(Integer user_id, CertificationRequestDTO cDTO){
        Profile profile = getUserProfileById(user_id);
        Certification certif = CertificationRequestDTO.mapToCertification(cDTO);
        certif.setProfile(profile);
        certificationRepository.save(certif);
        return certif;
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
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Skill>> skills = skillRepository.findAllByProfileId(profileId);
        return skills.orElse(Collections.emptyList());
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

    public Skill getSkill(Integer userId, Integer skillId){
        Integer profileId = getUserProfileById(userId).getId();
        return skillRepository.findByProfileIdAndId(profileId,skillId).orElseThrow(
                () -> new NotFoundException("Skill Not Found")
        );
    }

    @Transactional
    public Skill updateSkill(Integer userId,Integer skillId, SkillRequestDTO cDTO){
        Skill certification=  getSkill(userId,skillId);
        Skill update = SkillRequestDTO.mapToSkill(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return skillRepository.save(update);
    }

    // Get languages for a user
    public List<Language> getLanguages(Integer userId) {
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Language>> languages = languageRepository.findAllByProfileId(profileId);
        return languages.orElse(Collections.emptyList());
    }

    // Add a language
    @Transactional
    public Language addLanguage(Integer userId, LanguageRequestDTO lDTO) {
        Profile profile = getUserProfileById(userId);
        Language language = LanguageRequestDTO.mapToLanguage(lDTO);
        language.setProfile(profile);
        return languageRepository.save(language);
    }

    // Delete a language
    @Transactional
    public void deleteLanguage(Integer languageId) {
        languageRepository.findById(languageId).orElseThrow(
                () -> new NotFoundException("Language Not Found")
        );
        languageRepository.deleteById(languageId);
    }

    public Language getLanguage(Integer userId, Integer languageId){
        Integer profileId = getUserProfileById(userId).getId();
        return languageRepository.findByProfileIdAndId(profileId,languageId).orElseThrow(
                () -> new NotFoundException("Language Not Found")
        );
    }

    @Transactional
    public Language updateLanguage(Integer userId,Integer languageId, LanguageRequestDTO cDTO){
        Language certification=  getLanguage(userId,languageId);
        Language update = LanguageRequestDTO.mapToLanguage(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return languageRepository.save(update);
    }

    // Get education for a user
    public List<Education> getEducations(Integer userId) {
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Education>> educations = educationRepository.findAllByProfileId(profileId);
        return educations.orElse(Collections.emptyList());
    }

    // Add an education
    @Transactional
    public Education addEducation(Integer userId, EducationRequestDTO eDTO) {
        Profile profile = getUserProfileById(userId);
        Education education = EducationRequestDTO.mapToEducation(eDTO);
        education.setProfile(profile);
        return educationRepository.save(education);
    }

    // Delete an education
    @Transactional
    public void deleteEducation(Integer educationId) {
        educationRepository.findById(educationId).orElseThrow(
                () -> new NotFoundException("Education Not Found")
        );
        educationRepository.deleteById(educationId);
    }

    public Education getEducation(Integer userId, Integer educationId){
        Integer profileId = getUserProfileById(userId).getId();
        return educationRepository.findByProfileIdAndId(profileId,educationId).orElseThrow(
                () -> new NotFoundException("Education Not Found")
        );
    }

    @Transactional
    public Education updateEducation(Integer userId,Integer educationId, EducationRequestDTO cDTO){
        Education certification=  getEducation(userId,educationId);
        Education update = EducationRequestDTO.mapToEducation(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return educationRepository.save(update);
    }

    // Get experiences for a user
    public List<Experience> getExperiences(Integer userId) {
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Experience>> experiences = experienceRepository.findAllByProfileId(profileId);
        return experiences.orElse(Collections.emptyList());
    }

    // Add an experience
    @Transactional
    public Experience addExperience(Integer userId, ExperienceRequestDTO eDTO) {
        Profile profile = getUserProfileById(userId);
        Experience experience = ExperienceRequestDTO.mapToExperience(eDTO);
        experience.setProfile(profile);
        experienceRepository.save(experience);
        return experience;
    }

    // Delete an experience
    @Transactional
    public void deleteExperience(Integer experienceId) {
        experienceRepository.findById(experienceId).orElseThrow(
                () -> new NotFoundException("Experience Not Found")
        );
        experienceRepository.deleteById(experienceId);
    }

    public Experience getExperience(Integer userId, Integer experienceId){
        Integer profileId = getUserProfileById(userId).getId();
        return experienceRepository.findByProfileIdAndId(profileId,experienceId).orElseThrow(
                () -> new NotFoundException("Experience Not Found")
        );
    }

    @Transactional
    public Experience updateExperience(Integer userId,Integer experienceId, ExperienceRequestDTO cDTO){
        Experience certification=  getExperience(userId,experienceId);
        Experience update = ExperienceRequestDTO.mapToExperience(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return experienceRepository.save(update);
    }
    
    // Get projects for a user
    public List<Project> getProjects(Integer userId) {
        Integer profileId = getUserProfileById(userId).getId();
        Optional<List<Project>> projects = projectRepository.findAllByProfileId(profileId);
        return projects.orElse(Collections.emptyList());
    }

    // Add a project
    @Transactional
    public Project addProject(Integer userId, ProjectRequestDTO pDTO) {
        Profile profile = getUserProfileById(userId);
        Project project = ProjectRequestDTO.mapToProject(pDTO);
        project.setProfile(profile);
        projectRepository.save(project);
        return project;
    }

    // Delete a project
    @Transactional
    public void deleteProject(Integer projectId) {
        projectRepository.findById(projectId).orElseThrow(
                () -> new NotFoundException("Project Not Found")
        );
        projectRepository.deleteById(projectId);
    }

    public Project getProject(Integer userId, Integer projectId){
        Integer profileId = getUserProfileById(userId).getId();
        return projectRepository.findByProfileIdAndId(profileId,projectId).orElseThrow(
                () -> new NotFoundException("Project Not Found")
        );
    }

    @Transactional
    public Project updateProject(Integer userId,Integer projectId, ProjectRequestDTO cDTO){
        Project certification=  getProject(userId,projectId);
        Project update = ProjectRequestDTO.mapToProject(cDTO);
        update.setCreatedAt(certification.getCreatedAt());
        update.setProfile(certification.getProfile());
        update.setId(certification.getId());
        return projectRepository.save(update);
    }

}
