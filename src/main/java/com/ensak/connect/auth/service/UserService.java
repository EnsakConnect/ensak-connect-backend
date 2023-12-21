package com.ensak.connect.auth.service;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(RegisterRequest registerRequest){
        Profile profile = Profile.builder().fullName(registerRequest.getFullname()).build();

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password( passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .profile(profile)
                .profileType(registerRequest.getRole())
                .build();
        // call the profile service to create an empty profile

        user = userRepository.save(user);
        //profileService.createEmptyProfile(user, registerRequest.getFullname());


        return user;
    }

    public User updateEmail(Integer id,String email){
        User user = getUserById(id);
        user.setEmail(email);
        //if the user changes his email it will require an identity check
        user.setActivatedAt(null);
        return userRepository.save(user);
    }

    public User activateUser(String email){
        User user = getUserByEmail(email);
        user.setActivatedAt(new Date());
        return userRepository.save(user);
    }

    public void updatePassword(Integer id, String password){
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
    }

    public void deleteUserById(Integer id){
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public void deleteUserByEmail(String email){
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }
}
