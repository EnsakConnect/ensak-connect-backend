package com.ensak.connect.test;

import com.ensak.connect.test.dto.TestRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;
    public void addTest(TestRequest request) {
        TestEntity test = TestEntity.builder()
                .test(request.test())
                .build();
        testRepository.save(test);
    }
}
