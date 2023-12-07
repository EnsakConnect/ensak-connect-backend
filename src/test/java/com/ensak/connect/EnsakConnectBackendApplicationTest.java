package com.ensak.connect;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ensak.connect.job_post.service.JobPostService;

class EnsakConnectBackendApplicationTest {
    /**
     * Method under test: {@link EnsakConnectBackendApplication#commandLineRunner(JobPostService)}
     */
//    @Test
//    void testCommandLineRunner() throws Exception {
//        EnsakConnectBackendApplication ensakConnectBackendApplication = new EnsakConnectBackendApplication();
//
//        JobPost jobPost = new JobPost();
//        jobPost.setCreateDate(LocalDate.of(1970, 1, 1));
//        jobPost.setDescription("The characteristics of someone or something");
//        jobPost.setId(1);
//        jobPost.setTitle("Dr");
//        JobPostRepository jobPostRepository = mock(JobPostRepository.class);
//        when(jobPostRepository.save(Mockito.<JobPost>any())).thenReturn(jobPost);
//        ensakConnectBackendApplication.commandLineRunner(new JobPostService(jobPostRepository)).run("Args");
//        verify(jobPostRepository, atLeast(1)).save(Mockito.<JobPost>any());
//    }
//
//    /**
//     * Method under test: {@link EnsakConnectBackendApplication#commandLineRunner(JobPostService)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testCommandLineRunner2() throws Exception {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "com.ensak.connect.job_post.service.JobPostService.save(com.ensak.connect.job_post.dto.JobPostRequest)" because "jobPostService" is null
//        //       at com.ensak.connect.EnsakConnectBackendApplication.lambda$commandLineRunner$0(EnsakConnectBackendApplication.java:28)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        (new EnsakConnectBackendApplication()).commandLineRunner(null).run("Args");
//    }
}
