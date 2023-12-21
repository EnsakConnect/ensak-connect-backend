package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.CertificationRequestDTO;
import com.ensak.connect.profile.model.Certification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CertificationControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;
    @Test
    public void itShouldGetCertificationList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addCertification(dummyUser.getId(),
                CertificationRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .build());
        List<Certification> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Certification.builder()
                        .name("React")
                        .link("http://link.com")
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/certifications")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Certification> response = objectMapper.readValue(responseJson,new TypeReference<List<Certification>>(){});
        assertEquals(response.get(0).getLink(),expectedResponse.get(0).getLink());
        assertEquals(response.get(0).getName(),expectedResponse.get(0).getName());

    }

    @Test
    public void itShouldAddCertification() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Certification expectedResponse = Certification.builder()
                .name("React")
                .link("http://link.com")
                .build();
        //request

        CertificationRequestDTO payload = CertificationRequestDTO.builder()
                .name("React")
                .link("http://link.com")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/certifications")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Certification response = objectMapper.readValue(responseJson,Certification.class);
        assertEquals(response.getLink(),expectedResponse.getLink());
        assertEquals(response.getName(),expectedResponse.getName());

    }

    @Test
    public void itShouldNotAddCertification() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        //request

        CertificationRequestDTO payload = CertificationRequestDTO.builder()
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/certifications")
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
    public void itShouldDeleteUserCertification() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Certification certification = profileService.addCertification(dummyUser.getId(),
                CertificationRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/certifications/"+certification.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getCertifications(dummyUser.getId()));
        assertTrue(profileService.getCertifications(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldNotDeleteUserCertification() throws Exception{
        //setup
        this.authenticateAsStudent();
        var user = this.createDummyUser();
        Certification certification = profileService.addCertification(user.getId(),
                CertificationRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/certifications/"+certification.getId())
                )
                .andExpect(status().isNotFound());
    }

}
