package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.ProfileRequestDTO;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.profile.dto.SkillRequestDTO;
import com.ensak.connect.profile.models.Certification;
import com.ensak.connect.profile.models.Profile;
import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.profile.models.util.Level;
import com.ensak.connect.profile.repositories.ProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest extends AuthenticatedBaseIntegrationTest {
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
        String responseJson = api.perform(get("/api/v1/profile")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProfileResponseDTO profileResponseDTO = objectMapper.readValue(responseJson,ProfileResponseDTO.class);

        assertEquals(profileResponseDTO.getFullName(),ExpectedProfile.getFullName());
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
                get("/api/v1/profile/detailed")
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
    public void itShouldUpdateProfile() throws Exception{
        //setup
        this.authenticateAsUser();
        ProfileResponseDTO expectedResponse = ProfileResponseDTO.builder()
                .fullName("user fullname")
                .city("Kenitra")
                .address("AVN des FAR")
                .phone("0680668394")
                .titre("Frontend React Dev")
                .build();
        ProfileRequestDTO payload = ProfileRequestDTO.builder()
                .fullName("user fullname")
                .city("Kenitra")
                .address("AVN des FAR")
                .phone("0680668394")
                .titre("Frontend React Dev")
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
        assertEquals(response.getFullName(),expectedResponse.getFullName());

        assertEquals(response.getCity(),expectedResponse.getCity());

    }

    @Test
    public void itShouldGetSkillList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addSkill(dummyUser.getId(),
                SkillRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());
        List<Skill> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Skill.builder()
                .name("React")
                .level(Level.EXPERT)
                .build());

        //request
        String responseJson = api.perform(
                get("/api/v1/profile/skill")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        

    }


}