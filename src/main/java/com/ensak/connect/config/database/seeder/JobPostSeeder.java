package com.ensak.connect.config.database.seeder;

import com.ensak.connect.auth.dto.RegisterRequest;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.auth.repository.UserRepository;
import com.ensak.connect.auth.service.UserService;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.job_post.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobPostSeeder implements CommandLineRunner {
    @Autowired
    JobPostRepository jobPostRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createJobPosts();
    }

    private void createJobPosts() throws UserNotFoundException {

        User author;
        if (userRepository.findByEmail("author.jobpost@ensakconnect.com").isPresent()) {
            author = userRepository.findByEmail("author.jobpost@ensakconnect.com").get();
        } else {
            author = userService.createUser(
                    RegisterRequest.builder()
                            .email("author.jobpost@ensakconnect.com")
                            .role("STUDENT")
                            .fullname("Demo User")
                            .password("password")
                            .build()
            );
            userService.activateUser(author.getEmail());
        }

        jobPostRepository.save(
                JobPost.builder()
                        .title("Jop Post TiTle 1")
                        .companyName("Ensak Connect")
                        .location("Kenitra")
                        .author(author)
                        .companyType("Startup")
                        .category("PFE")
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
                        .tags(List.of(new String[]{"Java", "Spring boot", "Test"}))
                        .build()
        );

        jobPostRepository.save(
                JobPost.builder()
                        .title("Jop Post TiTle 2")
                        .companyName("Ensak")
                        .location("Kenitra")
                        .author(author)
                        .companyType("School")
                        .category("Doctorate")
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
                        .tags(List.of(new String[]{"Academic", "Theory", "Test"}))
                        .build()
        );

        jobPostRepository.save(
                JobPost.builder()
                        .title("Jop Post TiTle 3")
                        .companyName("Company")
                        .location("Unknown")
                        .author(author)
                        .companyType("IT")
                        .category("CDI")
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
                        .tags(List.of(new String[]{"Node", "Angular", "TDD"}))
                        .build()
        );

        jobPostRepository.save(
                JobPost.builder()
                        .title("Jop Post TiTle 4")
                        .companyName("Company 2")
                        .location("Unknown")
                        .author(author)
                        .companyType("Bank")
                        .category("CDI")
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
                        .tags(List.of(new String[]{"Node", "Angular", "TDD"}))
                        .build()
        );
    }
}
