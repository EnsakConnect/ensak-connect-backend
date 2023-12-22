package com.ensak.connect.integration.profile;

import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.*;
import com.ensak.connect.profile.model.*;
import com.ensak.connect.profile.model.util.ContractType;
import com.ensak.connect.profile.model.util.Level;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;

    @Test
    public void itShouldGetUserProfile() throws Exception {
        var dummyUser = this.authenticateAsUser();
        Profile ExpectedProfile = Profile.builder()
                .fullName("user fullname")
                .user(dummyUser)
                .build();
        String responseJson = api.perform(get("/api/v1/profile/"+dummyUser.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProfileResponseDTO profileResponseDTO = objectMapper.readValue(responseJson,ProfileResponseDTO.class);

        assertEquals(profileResponseDTO.getFullName(),ExpectedProfile.getFullName());
    }
    @Test
    public void itShouldNotFindTheProfile() throws Exception {
        var dummyUser = this.authenticateAsUser();
        Profile ExpectedProfile = Profile.builder()
                .fullName("user fullname")
                .user(dummyUser)
                .build();
        String responseJson = api.perform(get("/api/v1/profile/777")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldGetDetailedUserProfile() throws Exception {
        var dummyUser = this.authenticateAsUser();
        Profile ExpectedProfile = Profile.builder()
                .fullName("user fullname")
                .user(dummyUser)
                .certificationList(new ArrayList<>())
                .build();
        String responseJson = api.perform(
                get("/api/v1/profile/"+dummyUser.getId()+"/detailed")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Profile profileResponse = objectMapper.readValue(responseJson,Profile.class);

        assertEquals(profileResponse.getFullName(),ExpectedProfile.getFullName());
        assertEquals(profileResponse.getCertificationList(),ExpectedProfile.getCertificationList());
    }

    @Test
    public void itShouldNotFindTheDetailedProfile() throws Exception {
        var dummyUser = this.authenticateAsUser();
        Profile ExpectedProfile = Profile.builder()
                .fullName("user fullname")
                .user(dummyUser)
                .build();
        String responseJson = api.perform(get("/api/v1/profile/777/detailed")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
    @Test
    public void itShouldUpdateProfile() throws Exception{
        //setup
        this.authenticateAsUser();

        ProfileRequestDTO payload = ProfileRequestDTO.builder()
                .fullName("user fullname update")
                .phone("0555555555")
                .city("Kenitra")
                .address("adress")
                .title("Frontend React Dev")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);
        //request
        String responseJson = api.perform(
                put("/api/v1/profile")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonPayload)
                )
                        .andExpect(status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString();
        //assertions

        ProfileResponseDTO response = objectMapper.readValue(responseJson,ProfileResponseDTO.class);
        assertEquals(response.getFullName(),payload.getFullName());
        assertEquals(response.getTitle(),payload.getTitle());

    }

    @Test
    public void itShouldNotUpdateProfile() throws Exception{
        //setup
        this.authenticateAsUser();

        ProfileRequestDTO payload = ProfileRequestDTO.builder()
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);
        //request
        String responseJson = api.perform(
                        put("/api/v1/profile")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //HttpResponse response = objectMapper.readValue(responseJson, HttpResponse.class);
        //assertEquals(response.getMessage(),"{address=address is required, phone=phone is required, city=city is required, fullName=fullname is required, title=title is required}");
    }

}