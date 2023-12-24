package com.ensak.connect.config.database.seeder;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.question_post.model.QuestionPost;
import com.ensak.connect.question_post.repository.QuestionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuestionPostSeeder implements CommandLineRunner {

    @Autowired
    QuestionPostRepository questionPostRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String[] args) throws Exception {
        createQuestionPosts();
    }

    private void createQuestionPosts() throws UserNotFoundException {
        User author = userRepository.findByEmail("author.question@ensakconnect.com").orElseGet(
                () -> userService.createUser(
                        RegisterRequest.builder()
                                .email("author.question@ensakconnect.com")
                                .role("STUDENT")
                                .fullname("Demo User")
                                .password("password")
                                .build()
                )
        );
        userService.activateUser(author.getEmail());

        questionPostRepository.save(
                QuestionPost.builder()
                        .question("How to create a simple tags system on spring boot ?")
                        .tags(Arrays.asList("postgres", "code", "spring boot", "spring JPA"))
                        .author(author)
                        .build()
        );

        questionPostRepository.save(
                QuestionPost.builder()
                        .question("Is this a valid question ?")
                        .tags(Arrays.asList("question", "code", "spring boot"))
                        .author(author)
                        .build()
        );
    }

}
