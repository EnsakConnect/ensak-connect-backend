package com.ensak.connect.integration;

import com.ensak.connect.enumeration.Role;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public abstract class AuthenticatedBaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

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
        return userRepository.save(
            User.builder()
                .email("student.user@email.com")
                .password("password")
                .firstname("Student")
                .lastname("User")
                .role(Role.ROLE_USER)
                .build()
        );
    }

    protected User createDummyStudent() {
        return userRepository.save(
            User.builder()
                .email("student.user@email.com")
                .password("password")
                .firstname("Student")
                .lastname("User")
                .role(Role.ROLE_STUDENT)
                .build()
        );
    }

    protected User createDummyLaureate() {
        return userRepository.save(
            User.builder()
                .email("laureate.user@email.com")
                .password("password")
                .firstname("Laureate")
                .lastname("User")
                .role(Role.ROLE_LAUREATE)
                .build()
        );
    }

    protected User createDummyProfessor() {
        return userRepository.save(
            User.builder()
                .email("professor.user@email.com")
                .password("password")
                .firstname("Professor")
                .lastname("User")
                .role(Role.ROLE_PROFESSOR)
                .build()
        );
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
