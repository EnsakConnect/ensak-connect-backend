package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.ProjectRequestDTO;
import com.ensak.connect.profile.model.Project;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIntegrationTest extends AuthenticatedBaseIntegrationTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileService profileService;


    @Test
    public void itShouldGetProjectList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addProject(dummyUser.getId(),
                ProjectRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .description("big description")
                        .build());
        List<Project> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Project.builder()
                        .name("React")
                        .link("http://link.com")
                        .description("big description")
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/projects")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        List<Project> response = objectMapper.readValue(responseJson,new TypeReference<List<Project>>(){});
        assertEquals(response.get(0).getLink(),expectedResponse.get(0).getLink());
        assertEquals(response.get(0).getName(),expectedResponse.get(0).getName());
        assertEquals(response.get(0).getDescription(),expectedResponse.get(0).getDescription());

    }

    @Test
    public void itShouldAddProject() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Project expectedResponse = Project.builder()
                .name("React")
                .link("http://link.com")
                .description("big description")
                .build();
        //request

        ProjectRequestDTO payload = ProjectRequestDTO.builder()
                .name("React")
                .link("http://link.com")
                .description("big description")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/projects")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonPayload)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //assertions
        Project response = objectMapper.readValue(responseJson,Project.class);
        assertEquals(response.getLink(),expectedResponse.getLink());
        assertEquals(response.getName(),expectedResponse.getName());
        assertEquals(response.getDescription(),expectedResponse.getDescription());
    }

    @Test
    public void itShouldNotAddProject() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();


        ProjectRequestDTO payload = ProjectRequestDTO.builder()
                .description("big description")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/projects")
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
    public void itShouldDeleteUserProject() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Project project = profileService.addProject(dummyUser.getId(),
                ProjectRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .description("big description")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/projects/"+project.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getProjects(dummyUser.getId()));
        assertTrue(profileService.getProjects(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldNotDeleteUserProject() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        var user  = this.createDummyUser();
        Project project = profileService.addProject(user.getId(),
                ProjectRequestDTO.builder()
                        .name("React")
                        .link("http://link.com")
                        .description("big description")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/projects/"+project.getId())
                )
                .andExpect(status().isNotFound());
    }
}
