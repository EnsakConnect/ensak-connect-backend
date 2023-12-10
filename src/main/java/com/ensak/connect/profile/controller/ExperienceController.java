package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.CertificationRequestDTO;
import com.ensak.connect.profile.dto.ExperienceRequestDTO;
import com.ensak.connect.profile.model.Certification;
import com.ensak.connect.profile.model.Experience;
import com.ensak.connect.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ExperienceController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    @GetMapping("/experiences")
    public ResponseEntity<List<Experience>> getExperiences(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getExperiences(id), HttpStatus.OK);
    }

    @PostMapping("/experiences")
    public ResponseEntity<Experience> addExperience(@RequestBody @Valid ExperienceRequestDTO experienceRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Experience experience = profileService.addExperience(id,experienceRequestDTO);
        return new ResponseEntity<>(experience,HttpStatus.CREATED);
    }

    @PutMapping("/experiences/{experienceId}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Integer experienceId, @RequestBody @Valid ExperienceRequestDTO experienceRequestDTO){
        User user = authenticationService.getAuthenticatedUser();
        Experience response = profileService.updateExperience(user.getId(),experienceId ,experienceRequestDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/experiences/{experienceId}")
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
}
