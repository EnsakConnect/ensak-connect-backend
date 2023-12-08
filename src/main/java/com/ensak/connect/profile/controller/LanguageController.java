package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.exception.ForbiddenException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.LanguageRequestDTO;
import com.ensak.connect.profile.model.Language;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class LanguageController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages(){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        return new ResponseEntity<>(profileService.getLanguages(id), HttpStatus.OK);
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> addLanguage(@RequestBody @Valid LanguageRequestDTO languageRequestDTO){
        Integer id = authenticationService.getAuthenticatedUser().getId();
        Language language = profileService.addLanguage(id,languageRequestDTO);
        return new ResponseEntity<>(language,HttpStatus.CREATED);
    }

    @DeleteMapping("/languages/{languageId}")
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
}
