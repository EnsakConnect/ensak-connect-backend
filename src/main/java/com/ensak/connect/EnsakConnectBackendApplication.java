package com.ensak.connect;

import com.ensak.connect.job_post.JobPostRequest;
import com.ensak.connect.job_post.JobPostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EnsakConnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsakConnectBackendApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			JobPostService jobPostService
//	) {
//		return args -> {
//			var jobPost1 = JobPostRequest.builder()
//					.title("Jop Post TiTle 1")
//					.description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//					.build();
//			jobPostService.save(jobPost1);
//
//			var jobPost2 = JobPostRequest.builder()
//					.title("Jop Post TiTle 2")
//					.description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//					.build();
//			jobPostService.save(jobPost2);
//
//			var jobPost3 = JobPostRequest.builder()
//					.title("Jop Post TiTle 3")
//					.description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//					.build();
//			jobPostService.save(jobPost3);
//
//			var jobPost4 = JobPostRequest.builder()
//					.title("Jop Post TiTle 4")
//					.description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//					.build();
//			jobPostService.save(jobPost4);
//		};
	//}

}
