package com.ensak.connect.testTest;

import com.ensak.connect.test.dto.TestRequest;
import com.ensak.connect.test.dto.TestResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtString;

    @BeforeEach
    public void setup() throws Exception {
        String result = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"firstname\":\"Hamza\",\n" +
                        "\t\"lastname\":\"Alaoui\",\n" +
                        "\t\"password\":\"paswword\",\n" +
                        "\t\"email\":\"Hamza@gmail.com\",\n" +
                        "\t\"role\":\"ROLE_STUDENT\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        jwtString = parseJwtFromResponse(result);
    }

    private String parseJwtFromResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path("token").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void whenPostTest_thenReturnSuccessMessage() throws Exception {
        TestRequest testRequest = new TestRequest("Controller Test");
        String jsonRequest = objectMapper.writeValueAsString(testRequest);

        MvcResult result = mockMvc.perform(post("/api/ensak-connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtString))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        assertEquals("Post request works!", response.message());
    }

    @Test
    public void whenGetTest_thenReturnSuccessMessage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/ensak-connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtString))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        System.out.println("response content: "+ response.message());
        assertEquals("Get request works!", response.message());
   }
}