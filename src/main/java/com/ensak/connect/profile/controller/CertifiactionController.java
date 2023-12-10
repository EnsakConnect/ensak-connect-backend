package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.CertificationRequestDTO;
import com.ensak.connect.profile.model.Certification;
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
public class CertifiactionController {
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;
    @GetMapping("/certifications")
    public ResponseEntity<List<Certification>> getCertifications(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getCertifications(id), HttpStatus.OK);
    }

    @PostMapping("/certifications")
    public ResponseEntity<Certification> addCertification(@RequestBody @Valid CertificationRequestDTO certificationRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Certification certification = profileService.addCertification(id,certificationRequestDTO);
        return new ResponseEntity<>(certification,HttpStatus.CREATED);
    }

    @PutMapping("/certifications/{certificationId}")
    public ResponseEntity<Certification> updateCertification(@PathVariable Integer certificationId,@RequestBody @Valid CertificationRequestDTO certificationRequestDTO){
        User user = authenticationService.getAuthenticatedUser();
        Certification response = profileService.updateCertification(user.getId(),certificationId ,certificationRequestDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/certifications/{certificationId}")
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
}
