package com.ensak.connect.profile;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.dto.ProfileRequestDTO;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.profile.dto.SkillRequestDTO;
import com.ensak.connect.profile.models.Profile;
import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

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



}
