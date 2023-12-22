package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.LanguageRequestDTO;
import com.ensak.connect.profile.model.Language;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LanguageControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;
    @Test
    public void itShouldGetLanguageList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addLanguage(dummyUser.getId(),
                LanguageRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());
        List<Language> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Language.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/languages")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Language> response = objectMapper.readValue(responseJson,new TypeReference<List<Language>>(){});
        assertEquals(response.get(0).getLevel(),expectedResponse.get(0).getLevel());
        assertEquals(response.get(0).getName(),expectedResponse.get(0).getName());

    }

    @Test
    public void itShouldAddLanguage() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Language expectedResponse = Language.builder()
                .name("React")
                .level(Level.EXPERT)
                .build();
        //request

        LanguageRequestDTO payload = LanguageRequestDTO.builder()
                .name("React")
                .level(Level.EXPERT)
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/languages")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Language response = objectMapper.readValue(responseJson,Language.class);
        assertEquals(response.getLevel(),expectedResponse.getLevel());
        assertEquals(response.getName(),expectedResponse.getName());

    }

    @Test
    public void itShouldNotAddLanguage() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();

        LanguageRequestDTO payload = LanguageRequestDTO.builder()
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/languages")
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
    public void itShouldDeleteUserLanguage() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Language language = profileService.addLanguage(dummyUser.getId(),
                LanguageRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/languages/"+language.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getLanguages(dummyUser.getId()));
        assertTrue(profileService.getLanguages(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldNotDeleteUserLanguage() throws Exception{
        //setup
        var dummyUser = this.authenticateAsStudent();
        var user = this.createDummyUser();
        Language language = profileService.addLanguage(user.getId(),
                LanguageRequestDTO.builder()
                        .name("React")
                        .level(Level.EXPERT)
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/languages/"+language.getId())
                )
                .andExpect(status().isNotFound());
    }
}
