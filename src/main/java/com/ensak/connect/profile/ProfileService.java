package com.ensak.connect.profile;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.profile.dto.ProfileRequestDTO;
import com.ensak.connect.profile.models.Profile;
import com.ensak.connect.profile.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthenticationService authenticationService;

    public Profile createEmptyProfile(){
        Profile profile = new Profile();
        return profile;
    }

    public Profile createProfile(ProfileRequestDTO profileRequestDTO){
        Profile profile = new Profile();
        return profile;
    }

    public Profile updateProfile(ProfileRequestDTO profileRequestDTO){
        Profile profile = new Profile();
        return profile;
    }


}
