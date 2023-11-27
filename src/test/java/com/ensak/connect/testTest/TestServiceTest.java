package com.ensak.connect.testTest;

import com.ensak.connect.test.dto.TestRequest;
import com.ensak.connect.test.TestEntity;
import com.ensak.connect.test.TestRepository;
import com.ensak.connect.test.TestService;
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