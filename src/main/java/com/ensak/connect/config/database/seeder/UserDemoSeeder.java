package com.ensak.connect.config.database.seeder;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDemoSeeder implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Override
    public void run(String[] args) throws Exception {
        if(userRepository.findByEmail("demo@ensakconnect.com").isEmpty())
            createDemoUser();
    }

    private void createDemoUser() {
        User demoUser = userService.createUser(
                RegisterRequest.builder()
                        .email("demo@ensakconnect.com")
                        .role("STUDENT")
                        .fullname("Demo User")
                        .password("password")
                        .build()
        );
        userService.activateUser(demoUser.getEmail());
    }
}
