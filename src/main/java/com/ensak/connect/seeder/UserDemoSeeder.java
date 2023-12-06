package com.ensak.connect.seeder;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.enumeration.Role;
import com.ensak.connect.user.User;
import com.ensak.connect.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDemoSeeder implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Override
    public void run(String[] args) throws Exception {
        createDemoUser();
    }

    private void createDemoUser() {
        User demoUser = userService.createUser(
                RegisterRequest.builder()
                        .email("demo@ensakconnect.com")
                        .role(Role.ROLE_USER)
                        .fullname("Demo User")
                        .password("password")
                        .build()
        );
        userService.activateUser(demoUser.getEmail());
    }
}
