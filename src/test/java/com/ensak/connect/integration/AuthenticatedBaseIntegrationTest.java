package com.ensak.connect.integration;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public abstract class AuthenticatedBaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

//    @BeforeEach
//    public void setup() {
//        User testUser = userRepository.save(
//                User.builder()
//                        .email("test.user@email.com")
//                        .password("password")
//                        .firstname("Test")
//                        .lastname("User")
//                        .role(Role.ROLE_STUDENT)
//                        .build()
//        );
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    protected User createDummyUser() {
        var user = userRepository.findByEmail("user.user@email.com");
        return user.orElseGet(() -> userService.createUser(
                RegisterRequest.builder()
                        .email("user.user@email.com")
                        .password("password")
                        .fullname("user fullname")
                        .role("STUDENT")
                        .build()
        ));
    }

    protected User createDummyStudent() {
        var user = userRepository.findByEmail("student.user@email.com");
        return user.orElseGet(() -> userService.createUser(
                RegisterRequest.builder()
                        .email("student.user@email.com")
                        .password("password")
                        .fullname("student fullname")
                        .role("STUDENT")
                        .build()
        ));
    }

    protected User createDummyLaureate() {
        var user = userRepository.findByEmail("laureate.user@email.com");
        return user.orElseGet(() -> userService.createUser(
                RegisterRequest.builder()
                        .email("laureate.user@email.com")
                        .password("password")
                        .fullname("laureate fullname")
                        .role("LAUREATE")
                        .build()
        ));
    }

    protected User createDummyProfessor() {
        var user = userRepository.findByEmail("professor.user@email.com");
        return user.orElseGet(() -> userService.createUser(
                RegisterRequest.builder()
                        .email("professor.user@email.com")
                        .password("password")
                        .fullname("professor fullname")
                        .role("PROFESSOR")
                        .build()
        ));
    }

    protected User authenticateAs(User user) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }

    protected User authenticateAsUser() {
        User user = createDummyUser();
        return this.authenticateAs(user);
    }

    protected void logoutUser() {
        SecurityContextHolder.clearContext();
    }

    protected User authenticateAsStudent() {
        User user = createDummyStudent();
        return this.authenticateAs(user);
    }

    protected User authenticateAsLaureate() {
        User user = createDummyLaureate();
        return this.authenticateAs(user);
    }

    protected User authenticateAsProfessor() {
        User user = createDummyProfessor();
        return this.authenticateAs(user);
    }
}
