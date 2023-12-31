package com.ensak.connect.profile.controller;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.LanguageRequestDTO;
import com.ensak.connect.profile.model.Language;
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

    @PutMapping("/languages/{languageId}")
    public ResponseEntity<Language> updateLanguage(@PathVariable Integer languageId, @RequestBody @Valid LanguageRequestDTO languageRequestDTO){
        User user = authenticationService.getAuthenticatedUser();
        Language response = profileService.updateLanguage(user.getId(),languageId ,languageRequestDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("/languages/{languageId}")
    public ResponseEntity deleteLanguage(@PathVariable Integer languageId) throws NotFoundException {
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        boolean found =  profileService.getLanguages(userId).stream().anyMatch(language ->
                language.getId().equals(languageId)
        );
        if(found){
            profileService.deleteLanguage(languageId);
        }
        else throw new NotFoundException("Language not Found");
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }
}
