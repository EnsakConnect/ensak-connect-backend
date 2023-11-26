package com.ensak.connect.service;

import com.ensak.connect.domain.TestEntity;
import com.ensak.connect.dto.TestRequest;
import com.ensak.connect.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TestServiceTest {

    @Autowired
    private TestService testService;

    @Autowired
    private TestRepository testRepository;

    @Test
    void addTest() {
        TestRequest request = new TestRequest("Service Test");
        testService.addTest(request);

        TestEntity retrievedTest = testRepository.findAll().stream()
                .filter(test -> "Service Test".equals(test.getTest()))
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedTest);
        assertEquals("Service Test", retrievedTest.getTest());
    }
}