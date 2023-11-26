package com.ensak.connect.controller;

import com.ensak.connect.dto.TestRequest;
import com.ensak.connect.dto.TestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenPostTest_thenReturnSuccessMessage() throws Exception {
        TestRequest testRequest = new TestRequest("Controller Test");
        String jsonRequest = objectMapper.writeValueAsString(testRequest);

        MvcResult result = mockMvc.perform(post("/api/ensak-connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        assertEquals("Post request works!", response.message());
    }

    @Test
    public void whenGetTest_thenReturnSuccessMessage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/ensak-connect"))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        assertEquals("Get request works!", response.message());
   }
}