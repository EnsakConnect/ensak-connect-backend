package com.ensak.connect.testTest;



import com.ensak.connect.test.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.ensak.connect.test.TestEntity;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TestRepositoryTest {
    @Autowired
    private TestRepository testRepository;

    @Test
    public void saveTest_thenRetrieveTest() {
        TestEntity savedTest = testRepository.save(new TestEntity(null, "Sample Test"));
        assertNotNull(savedTest.getId());
        assertEquals("Sample Test", savedTest.getTest());
    }
}