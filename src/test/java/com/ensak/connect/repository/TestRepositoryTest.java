package com.ensak.connect.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.ensak.connect.domain.TestEntity;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TestRepositoryTest {
    @Autowired
    private TestRepository testRepository;

    @Test
    public void itShouldSaveEntityToDatabase() {
        String testName = "Sample Test";
        TestEntity test = new TestEntity(testName);
        TestEntity savedTest = testRepository.save(test);
        assertNotNull(savedTest.getId());
        assertEquals(testName, savedTest.getTest());
    }
}