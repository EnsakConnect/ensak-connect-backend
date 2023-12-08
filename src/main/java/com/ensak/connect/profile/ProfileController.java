package com.ensak.connect.profile;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.dto.*;
import com.ensak.connect.profile.models.*;
import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.resource.ResourceService;
import com.ensak.connect.resource.ResourceType;
import com.ensak.connect.resource.model.ResourceOwner;
import com.ensak.connect.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    private final ResourceService resourceService;

    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfile(){
        User user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());
        return new ResponseEntity<>(ProfileResponseDTO.mapToDTO(profile), HttpStatus.OK);
    }

    @GetMapping("/detailed")
    public ResponseEntity<Profile> getDetailedProfile(){
        User user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Profile profile = profileService.updateProfile(id,profileRequestDTO);
        return new ResponseEntity<>(ProfileResponseDTO.mapToDTO(profile),HttpStatus.OK);
    }

    @GetMapping("/skill")
    public ResponseEntity<List<Skill>> getSkills(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getSkills(id),HttpStatus.OK);
    }

    @PostMapping("/skill")
    public ResponseEntity<Skill> addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Skill skill = profileService.addSkill(id,skillRequestDTO);
        return new ResponseEntity<>(skill,HttpStatus.CREATED);
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseEntity deleteSkill(@PathVariable Integer skillId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getSkills(userId).stream().anyMatch(skill ->
            skill.getId().equals(skillId)
        );
        if(found){
            profileService.deleteSkill(skillId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/certification")
    public ResponseEntity<List<Certification>> getCertifications(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getCertifications(id),HttpStatus.OK);
    }

    @PostMapping("/certification")
    public ResponseEntity<Certification> addCertification(@RequestBody @Valid CertificationRequestDTO certificationRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Certification certification = profileService.addCertification(id,certificationRequestDTO);
        return new ResponseEntity<>(certification,HttpStatus.CREATED);
    }

    @DeleteMapping("/certification/{certificationId}")
    public ResponseEntity deleteCertification(@PathVariable Integer certificationId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getCertifications(userId).stream().anyMatch(certification ->
                certification.getId().equals(certificationId)
        );
        if(found){
            profileService.deleteCertification(certificationId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/education")
    public ResponseEntity<List<Education>> getEducations(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getEducations(id),HttpStatus.OK);
    }

    @PostMapping("/education")
    public ResponseEntity<Education> addEducation(@RequestBody @Valid EducationRequestDTO educationRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Education education = profileService.addEducation(id,educationRequestDTO);
        return new ResponseEntity<>(education,HttpStatus.CREATED);
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity deleteEducation(@PathVariable Integer educationId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getEducations(userId).stream().anyMatch(education ->
                education.getId().equals(educationId)
        );
        if(found){
            profileService.deleteEducation(educationId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/experience")
    public ResponseEntity<List<Experience>> getExperiences(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getExperiences(id),HttpStatus.OK);
    }

    @PostMapping("/experience")
    public ResponseEntity<Experience> addExperience(@RequestBody @Valid ExperienceRequestDTO experienceRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Experience experience = profileService.addExperience(id,experienceRequestDTO);
        return new ResponseEntity<>(experience,HttpStatus.CREATED);
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity deleteExperience(@PathVariable Integer experienceId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getExperiences(userId).stream().anyMatch(experience ->
                experience.getId().equals(experienceId)
        );
        if(found){
            profileService.deleteExperience(experienceId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/language")
    public ResponseEntity<List<Language>> getLanguages(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getLanguages(id),HttpStatus.OK);
    }

    @PostMapping("/language")
    public ResponseEntity<Language> addLanguage(@RequestBody @Valid LanguageRequestDTO languageRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Language language = profileService.addLanguage(id,languageRequestDTO);
        return new ResponseEntity<>(language,HttpStatus.CREATED);
    }

    @DeleteMapping("/language/{languageId}")
    public ResponseEntity deleteLanguage(@PathVariable Integer languageId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getLanguages(userId).stream().anyMatch(language ->
                language.getId().equals(languageId)
        );
        if(found){
            profileService.deleteLanguage(languageId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/project")
    public ResponseEntity<List<Project>> getProjects(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getProjects(id),HttpStatus.OK);
    }

    @PostMapping("/project")
    public ResponseEntity<Project> addProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Project project = profileService.addProject(id,projectRequestDTO);
        return new ResponseEntity<>(project,HttpStatus.CREATED);
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity deleteProject(@PathVariable Integer projectId) throws ForbiddenException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getProjects(userId).stream().anyMatch(project ->
                project.getId().equals(projectId)
        );
        if(found){
            profileService.deleteProject(projectId);
        }
        else throw new ForbiddenException("Can not modify other user's profile");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @PutMapping("/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("picture") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());

        List<Resource> resources = resourceService.getAllOwnerResource(profile,ResourceType.ProfilePicture);
        Resource resource;
        if(resources == null || resources.isEmpty()){
            resource = resourceService.createResource(profile, ResourceType.ProfilePicture, file);
        }
        else {
            resource = resources.get(0);
            resource = resourceService.updateResource(resource,ResourceType.ProfilePicture,file);
        }


        Map<String, String> response = new HashMap<>();
        response.put("profile-picture", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
