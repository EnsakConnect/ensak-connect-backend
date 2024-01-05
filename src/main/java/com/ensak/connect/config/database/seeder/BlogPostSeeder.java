package com.ensak.connect.config.database.seeder;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlogPostSeeder implements CommandLineRunner {
    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createBlogPosts();
    }

    private void createBlogPosts() throws UserNotFoundException {

        User author;
        if (userRepository.findByEmail("author.blogpost@ensakconnect.com").isPresent()) {
            author = userRepository.findByEmail("author.blogpost@ensakconnect.com").get();
        } else {
            author = userService.createUser(
                    RegisterRequest.builder()
                            .email("author.blogpost@ensakconnect.com")
                            .role("STUDENT")
                            .fullname("Demo User")
                            .password("password")
                            .build()
            );
            userService.activateUser(author.getEmail());
        }

        blogPostRepository.save(
                BlogPost.builder()
                        .content("Blog Post Content 1")
                        .tags(List.of(new String[]{"Java", "Spring boot", "Test"}))
                        .build()
        );

        blogPostRepository.save(
                BlogPost.builder()
                        .content("Blog Post Content 2")
                        .tags(List.of(new String[]{"Java", "Spring boot", "Test"}))
                        .build()
        );

        blogPostRepository.save(
                BlogPost.builder()
                        .content("Blog Post Content 3")
                        .tags(List.of(new String[]{"Java", "Spring boot", "Test"}))
                        .build()
        );

        blogPostRepository.save(
                BlogPost.builder()
                        .content("Blog Post Content 4")
                        .tags(List.of(new String[]{"Java", "Spring boot", "Test"}))
                        .build()
        );
    }
}
