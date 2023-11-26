package com.ensak.connect.service;

import com.ensak.connect.domain.TestEntity;
import com.ensak.connect.dto.TestRequest;
import com.ensak.connect.repository.TestRepository;
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
