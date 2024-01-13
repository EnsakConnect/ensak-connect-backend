package com.ensak.connect.profile.controller;


import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.ProfileDetailResponseDTO;
import com.ensak.connect.profile.dto.ProfileRequestDTO;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.profile.model.Profile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Integer userId){
        ProfileResponseDTO profile = profileService.getSummaryProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/search/{fullname}")
    public ResponseEntity<Page<ProfileResponseDTO>> getProfiles(
            @PathVariable String fullname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageRequest =
                PageRequest.of(page, size, Sort.by("fullName").descending());
        Page<ProfileResponseDTO> profiles = profileService.getSearchProfiles(fullname, pageRequest);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/{userId}/detailed")
    public ResponseEntity<ProfileDetailResponseDTO> getDetailedProfile(@PathVariable Integer userId){
        ProfileDetailResponseDTO profile = profileService.getDetailedProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProfileDetailResponseDTO> updateProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Profile profile = profileService.updateProfile(id,profileRequestDTO);
        return new ResponseEntity<>(ProfileDetailResponseDTO.mapToDTO(profile),HttpStatus.OK);
    }


    @PutMapping("/profile-picture/{resource_id}")
    public ResponseEntity updateProfilePicture(@PathVariable Integer resource_id) {

        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.updateProfilePicture(resource_id,user);

        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }
    @PutMapping("/profile-picture")
    public ResponseEntity noProfilePicture() {

        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.unuseProfilePicture(user);

        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }

    @PutMapping("/banner/{resource_id}")
    public ResponseEntity<?> updateBanner(@PathVariable Integer resource_id) {
        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.updateBanner(resource_id, user);
        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }

    @PutMapping("/banner")
    public ResponseEntity<?> noBanner() {
        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.unuseBanner(user);
        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }
    @PutMapping("/resume/{resource_id}")
    public ResponseEntity<?> updateResume(@PathVariable Integer resource_id) {
        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.updateResume(resource_id, user);
        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }

    @PutMapping("/resume")
    public ResponseEntity<?> noResume() {
        var user = authenticationService.getAuthenticatedUser();
        Profile profile = profileService.unuseResume(user);
        return ResponseEntity.ok(ProfileResponseDTO.mapToDTO(profile));
    }

     /*
    @DeleteMapping("/profile-picture")
    public ResponseEntity<?> deleteProfilePicture(){
        User user = this.authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());
        profileService.deleteProfilePicture(profile);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/banner")
    public ResponseEntity<?> uploadBanner(@RequestParam("banner") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();
        Resource resource = profileService.handleProfileResourceUpload(user, ResourceType.Banner, file);


        Map<String, String> response = new HashMap<>();
        response.put("banner", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/banner")
    public ResponseEntity<?> deleteBanner(){
        User user = this.authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());
        profileService.deleteBanner(profile);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/resume")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();

        Resource resource = profileService.handleProfileResourceUpload(user, ResourceType.Resume, file);


        Map<String, String> response = new HashMap<>();
        response.put("resume", resource.getFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/resume")
    public ResponseEntity<?> deleteResume(){
        User user = this.authenticationService.getAuthenticatedUser();
        Profile profile = profileService.getUserProfileById(user.getId());
        profileService.deleteResume(profile);

        return ResponseEntity.noContent().build();
    }
    */
}
