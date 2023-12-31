package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.SkillRequestDTO;
import com.ensak.connect.profile.model.Skill;
import com.ensak.connect.auth.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class SkillController {
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;
    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getSkills(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getSkills(id), HttpStatus.OK);
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Skill skill = profileService.addSkill(id,skillRequestDTO);
        return new ResponseEntity<>(skill,HttpStatus.CREATED);
    }

    @PutMapping("/skills/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Integer skillId, @RequestBody @Valid SkillRequestDTO skillRequestDTO){
        User user = authenticationService.getAuthenticatedUser();
        Skill response = profileService.updateSkill(user.getId(),skillId ,skillRequestDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity deleteSkill(@PathVariable Integer skillId) throws NotFoundException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getSkills(userId).stream().anyMatch(skill ->
                skill.getId().equals(skillId)
        );
        if(found){
            profileService.deleteSkill(skillId);
        }
        else throw new NotFoundException("Skill not found");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

}
