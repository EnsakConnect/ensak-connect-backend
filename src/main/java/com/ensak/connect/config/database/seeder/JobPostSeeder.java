package com.ensak.connect.config.database.seeder;
//
//@Component
//public class JobPostSeeder implements CommandLineRunner {
//    @Autowired
//    JobPostService jobPostService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        createJobPosts();
//    }
//
//    private void createJobPosts() {
//        /**
//         * TODO: set the author
//         */
//        var jobPost1 = JobPostRequestDTO.builder()
//                .title("Jop Post TiTle 1")
//                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//                .build();
//        jobPostService.createJobPost(jobPost1);
//
//        var jobPost2 = JobPostRequestDTO.builder()
//                .title("Jop Post TiTle 2")
//                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//                .build();
//        jobPostService.createJobPost(jobPost2);
//
//        var jobPost3 = JobPostRequestDTO.builder()
//                .title("Jop Post TiTle 3")
//                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//                .build();
//        jobPostService.createJobPost(jobPost3);
//
//        var jobPost4 = JobPostRequestDTO.builder()
//                .title("Jop Post TiTle 4")
//                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam maximus, sem id commodo porta, neque leo sollicitudin elit, quis finibus quam velit sed magna. Mauris a lorem elit. Sed posuere tellus id nulla laoreet volutpat. Donec vel erat lacinia, viverra augue vel, pharetra dolor. Integer ornare nunc non scelerisque vestibulum. Fusce viverra nec erat sit amet efficitur. Cras imperdiet ipsum sed pharetra accumsan. Sed placerat, diam vitae faucibus iaculis, lorem est laoreet eros, quis posuere mauris risus vitae quam. Etiam tempor risus eget ex ultrices laoreet. Praesent faucibus velit condimentum nisi ullamcorper, ut tristique sem dapibus. In laoreet finibus felis ac ornare.")
//                .build();
//        jobPostService.createJobPost(jobPost4);
//    }
//}
