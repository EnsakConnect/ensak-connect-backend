package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.EducationRequestDTO;
import com.ensak.connect.profile.models.Education;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class EducationController {
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;
    @GetMapping("/educations")
    public ResponseEntity<List<Education>> getEducations(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getEducations(id), HttpStatus.OK);
    }

    @PostMapping("/educations")
    public ResponseEntity<Education> addEducation(@RequestBody @Valid EducationRequestDTO educationRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Education education = profileService.addEducation(id,educationRequestDTO);
        return new ResponseEntity<>(education,HttpStatus.CREATED);
    }

    @DeleteMapping("/educations/{educationId}")
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
}
