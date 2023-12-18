package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.ExperienceRequestDTO;
import com.ensak.connect.profile.model.Experience;
import com.ensak.connect.profile.model.util.ContractType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExperienceControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;

    @Test
    public void itShouldGetExperienceList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addExperience(dummyUser.getId(),
                ExperienceRequestDTO.builder()
                        .positionTitle("Full Stack")
                        .startDate(new Date())
                        .companyName("NONAME")
                        .location("Casa")
                        .contractType(ContractType.Internship)
                        .build());
        List<Experience> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Experience.builder()
                        .positionTitle("Full Stack")
                        .startDate(new Date())
                        .companyName("NONAME")
                        .location("Casa")
                        .contractType(ContractType.Internship)
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/experiences")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Experience> response = objectMapper.readValue(responseJson,new TypeReference<List<Experience>>(){});
        assertEquals(response.get(0).getPositionTitle(),expectedResponse.get(0).getPositionTitle());
        assertEquals(response.get(0).getContractType(),expectedResponse.get(0).getContractType());
        assertEquals(response.get(0).getCompanyName(),expectedResponse.get(0).getCompanyName());
    }

    @Test
    public void itShouldAddExperience() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Experience expectedResponse = Experience.builder()
                .positionTitle("Full Stack")
                .startDate(new Date())
                .companyName("NONAME")
                .location("Casa")
                .contractType(ContractType.Internship)
                .build();
        //request

        ExperienceRequestDTO payload = ExperienceRequestDTO.builder()
                .positionTitle("Full Stack")
                .startDate(new Date())
                .companyName("NONAME")
                .location("Casa")
                .contractType(ContractType.Internship)
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/experiences")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Experience response = objectMapper.readValue(responseJson,Experience.class);
        assertEquals(response.getPositionTitle(),expectedResponse.getPositionTitle());
        assertEquals(response.getContractType(),expectedResponse.getContractType());
        assertEquals(response.getCompanyName(),expectedResponse.getCompanyName());


    }

    @Test
    public void itShouldNotAddExperience() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        //request
        ExperienceRequestDTO payload = ExperienceRequestDTO.builder()
                .location("Casa")
                .contractType(ContractType.Internship)
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/experiences")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void itShouldDeleteUserExperience() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Experience experience = profileService.addExperience(dummyUser.getId(),
                ExperienceRequestDTO.builder()
                        .positionTitle("Full Stack")
                        .startDate(new Date())
                        .companyName("NONAME")
                        .location("Casa")
                        .contractType(ContractType.Internship)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/experiences/"+experience.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getExperiences(dummyUser.getId()));
        assertTrue(profileService.getExperiences(dummyUser.getId()).isEmpty());
    }


    @Test
    public void itShouldNotDeleteUserExperience() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        var user = this.createDummyUser();
        Experience experience = profileService.addExperience(user.getId(),
                ExperienceRequestDTO.builder()
                        .positionTitle("Full Stack")
                        .startDate(new Date())
                        .companyName("NONAME")
                        .location("Casa")
                        .contractType(ContractType.Internship)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/experiences/"+experience.getId())
                )
                .andExpect(status().isNotFound());
    }


}
