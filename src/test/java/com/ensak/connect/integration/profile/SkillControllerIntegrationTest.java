package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.SkillRequestDTO;
import com.ensak.connect.profile.model.Skill;
import com.ensak.connect.profile.model.util.Level;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SkillControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;


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
                        get("/api/v1/profile/skills")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Skill> response = objectMapper.readValue(responseJson,new TypeReference<List<Skill>>(){});
        assertEquals(response.get(0).getLevel(),expectedResponse.get(0).getLevel());
        assertEquals(response.get(0).getName(),expectedResponse.get(0).getName());

    }

    @Test
    public void itShouldAddSkill() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Skill expectedResponse = Skill.builder()
                .name("React")
                .level(Level.EXPERT)
                .build();
        //request

        SkillRequestDTO payload = SkillRequestDTO.builder()
                .name("React")
                .level(Level.EXPERT)
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/skills")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Skill response = objectMapper.readValue(responseJson,Skill.class);
        assertEquals(response.getLevel(),expectedResponse.getLevel());
        assertEquals(response.getName(),expectedResponse.getName());

    }

    @Test
    public void itShouldNotAddSkill() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        //request

        SkillRequestDTO payload = SkillRequestDTO.builder()
                .level(Level.EXPERT)
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/skills")
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
    public void itShouldDeleteUserSkill() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Skill skill = profileService.addSkill(dummyUser.getId(),
                SkillRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/skills/"+skill.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getSkills(dummyUser.getId()));
        assertTrue(profileService.getSkills(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldNotDeleteUserSkill() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        var user = this.createDummyUser();
        Skill skill = profileService.addSkill(user.getId(),
                SkillRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/skills/"+skill.getId())
                )
                .andExpect(status().isNotFound());
    }

}
