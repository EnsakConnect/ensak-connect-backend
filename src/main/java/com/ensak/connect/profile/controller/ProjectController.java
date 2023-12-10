package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.ProjectRequestDTO;
import com.ensak.connect.profile.model.Project;
import com.ensak.connect.auth.enums.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProjectController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getProjects(id), HttpStatus.OK);
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> addProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Project project = profileService.addProject(id,projectRequestDTO);
        return new ResponseEntity<>(project,HttpStatus.CREATED);
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectId, @RequestBody @Valid ProjectRequestDTO projectRequestDTO){
        User user = authenticationService.getAuthenticatedUser();
        Project response = profileService.updateProject(user.getId(),projectId ,projectRequestDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/projects/{projectId}")
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
}
