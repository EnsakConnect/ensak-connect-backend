package com.ensak.connect.integration.health;


import com.ensak.connect.health.HealthResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthIntegrationTest {
    @Autowired
    private MockMvc api;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void itShouldReturnSuccessWhenServerIsRunning() throws Exception {
        String responseJSON = api.perform(
                get("/api/v1/health")
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HealthResponseDTO response = objectMapper.readValue(responseJSON, HealthResponseDTO.class);

        assertTrue(response.getSuccess());
    }
}
