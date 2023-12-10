package com.ensak.connect.profile.controller;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.CertificationRequestDTO;
import com.ensak.connect.profile.dto.ProfileDetailResponseDTO;
import com.ensak.connect.profile.dto.ProfileRequestDTO;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.profile.model.Certification;
import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.resource.ResourceType;
import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfile(){
        User user = authenticationService.getAuthenticatedUser();
        ProfileResponseDTO profile = profileService.getSummaryProfile(user.getId());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Integer userId){
        ProfileResponseDTO profile = profileService.getSummaryProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


    @GetMapping("/detailed")
    public ResponseEntity<ProfileDetailResponseDTO> getDetailedProfile(){
        User user = authenticationService.getAuthenticatedUser();
        ProfileDetailResponseDTO profile = profileService.getDetailedProfile(user.getId());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/{userId}/detailed")
    public ResponseEntity<ProfileDetailResponseDTO> getDetailedProfile(@PathVariable Integer userId){
        ProfileDetailResponseDTO profile = profileService.getDetailedProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Profile profile = profileService.updateProfile(id,profileRequestDTO);
        return new ResponseEntity<>(ProfileResponseDTO.mapToDTO(profile),HttpStatus.OK);
    }

    @PutMapping("/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("picture") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();
        Resource resource = profileService.handleProfileResourceUpload(user, ResourceType.ProfilePicture, file);


        Map<String, String> response = new HashMap<>();
        response.put("profile-picture", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/banner")
    public ResponseEntity<?> uploadBanner(@RequestParam("banner") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();
        Resource resource = profileService.handleProfileResourceUpload(user, ResourceType.Banner, file);


        Map<String, String> response = new HashMap<>();
        response.put("banner", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/resume")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();

        Resource resource = profileService.handleProfileResourceUpload(user, ResourceType.Resume, file);


        Map<String, String> response = new HashMap<>();
        response.put("resume", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
