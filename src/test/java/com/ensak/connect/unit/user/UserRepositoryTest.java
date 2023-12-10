package com.ensak.connect.unit.user;

import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.enums.User;
import com.ensak.connect.auth.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    public void shouldSaveUser()

    {
        User user = User.builder()
                .role(Role.ROLE_USER)
                .email("test@gmail.com")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }
    @Test
    void itShouldfindUserByEmail() {

        // given
        String email = "test@gmail.com";

        User user = User.builder()
                .role(Role.ROLE_USER)
                .email(email)
                .password("password")
                .build();

        userRepository.save(user);

        // when

        Optional<User>  foundUser = userRepository.findByEmail(email);

        // then

        assertThat(foundUser.isPresent()).isTrue();
        assertEquals(user.getId(), foundUser.get().getId());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
        assertEquals(user.getEmail(), foundUser.get().getEmail());

    }

    @Test
    public void shouldNotFindUserByInvalidEmail() {
        Optional<User> foundUser = userRepository.findByEmail("invalid@example.com");

        assertThat(foundUser.isPresent()).isFalse();
    }


}