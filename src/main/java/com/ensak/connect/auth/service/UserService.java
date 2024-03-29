package com.ensak.connect.auth.service;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.dto.UserResponseDTO;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.config.exception.model.EmailExistException;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.model.util.ProfileType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @SneakyThrows
    @Transactional
    public User createUser(RegisterRequest registerRequest) {

        Optional<User> existing = userRepository.findByEmail(registerRequest.getEmail().toLowerCase());
        if (existing.isPresent()) {
            throw new EmailExistException("Email account already exists");
        }
        User user = User.builder()
                .email(registerRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .isNotLocked(true)
                .isActive(true)
                .build();

        user = userRepository.save(user);
        var profile = profileService.createEmptyProfile(user, registerRequest.getFullname(), ProfileType.valueOf(registerRequest.getRole()));
        user.setProfile(profile);
        return userRepository.save(user);
    }

    public User updateEmail(Integer id, String email) {
        User user = getUserById(id);
        user.setEmail(email);
        //if the user changes his email it will require an identity check
        user.setActivatedAt(null);
        return userRepository.save(user);
    }

    public User activateUser(String email) {
        User user = getUserByEmail(email);
        user.setActivatedAt(new Date());
        return userRepository.save(user);
    }

    public void updatePassword(Integer id, String password) {
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
    }

    public Page<UserResponseDTO> getUsersPage(Pageable pageable) {
        return UserResponseDTO.map(userRepository.getUsersPage(pageable));
    }

    @Transactional
    public void deleteUserById(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public void patchIsNotLockedById(Integer id) {
        User user = getUserById(id);
        user.setIsNotLocked(!user.getIsNotLocked());
        userRepository.save(user);
    }

    @Transactional
    public void patchIsActiveById(Integer id) {
        User user = getUserById(id);
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }
}
