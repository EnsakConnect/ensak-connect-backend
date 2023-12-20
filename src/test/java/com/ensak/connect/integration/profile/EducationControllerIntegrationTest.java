package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.EducationRequestDTO;
import com.ensak.connect.profile.model.Education;
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
public class EducationControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;


    @Test
    public void itShouldGetEducationList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addEducation(dummyUser.getId(),
                EducationRequestDTO.builder()
                        .field("informatique")
                        .degree("Engeneering")
                        .school("ENSA KENITRA")
                        .startDate(new Date())
                        .build());
        List<Education> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Education.builder()
                        .field("informatique")
                        .degree("Engeneering")
                        .school("ENSA KENITRA")
                        .startDate(new Date())
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/educations")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Education> response = objectMapper.readValue(responseJson,new TypeReference<List<Education>>(){});
        assertEquals(response.get(0).getDegree(),expectedResponse.get(0).getDegree());
        assertEquals(response.get(0).getField(),expectedResponse.get(0).getField());
        assertEquals(response.get(0).getSchool(),expectedResponse.get(0).getSchool());
    }

    @Test
    public void itShouldAddEducation() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Education expectedResponse = Education.builder()
                .field("informatique")
                .degree("Engeneering")
                .school("ENSA KENITRA")
                .startDate(new Date())
                .build();
        //request

        EducationRequestDTO payload = EducationRequestDTO.builder()
                .field("informatique")
                .degree("Engeneering")
                .school("ENSA KENITRA")
                .startDate(new Date())
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/educations")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Education response = objectMapper.readValue(responseJson,Education.class);
        assertEquals(response.getDegree(),expectedResponse.getDegree());
        assertEquals(response.getField(),expectedResponse.getField());
        assertEquals(response.getStartDate(),expectedResponse.getStartDate());
        assertEquals(response.getSchool(),expectedResponse.getSchool());

    }

    @Test
    public void itShouldNotAddEducation() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();

        EducationRequestDTO payload = EducationRequestDTO.builder()
                .school("ENSA KENITRA")
                .startDate(new Date())
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/educations")
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
    public void itShouldDeleteUserEducation() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Education education = profileService.addEducation(dummyUser.getId(),
                EducationRequestDTO.builder()
                        .field("informatique")
                        .degree("Engeneering")
                        .school("ENSA KENITRA")
                        .startDate(new Date())
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/educations/"+education.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getEducations(dummyUser.getId()));
        assertTrue(profileService.getEducations(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldNotDeleteUserEducation() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        var user = this.createDummyUser();
        Education education = profileService.addEducation(user.getId(),
                EducationRequestDTO.builder()
                        .field("informatique")
                        .degree("Engeneering")
                        .school("ENSA KENITRA")
                        .startDate(new Date())
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/educations/"+education.getId())
                )
                .andExpect(status().isNotFound());
    }

}
