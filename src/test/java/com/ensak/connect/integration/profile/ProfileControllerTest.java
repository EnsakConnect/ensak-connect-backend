package com.ensak.connect.integration.profile;

import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.dto.*;
import com.ensak.connect.profile.models.*;
import com.ensak.connect.profile.models.util.ContractType;
import com.ensak.connect.profile.models.util.Level;
import com.ensak.connect.profile.repositories.ProfileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                        post("/api/v1/profile/skill")
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
                delete("/api/v1/profile/skill/"+skill.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getSkills(dummyUser.getId()));
        assertTrue(profileService.getSkills(dummyUser.getId()).isEmpty());
    }

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
                        get("/api/v1/profile/language")
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
                        post("/api/v1/profile/Language")
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
                        delete("/api/v1/profile/Language/"+language.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getLanguages(dummyUser.getId()));
        assertTrue(profileService.getLanguages(dummyUser.getId()).isEmpty());
    }

    @Test
    public void itShouldGetCertificationList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addCertification(dummyUser.getId(),
                CertificationRequestDTO.builder()
                        .name("React")
                        .link("link")
                        .build());
        List<Certification> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Certification.builder()
                        .name("React")
                        .link("link")
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/Certification")
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
                .link("link")
                .build();
        //request

        CertificationRequestDTO payload = CertificationRequestDTO.builder()
                .name("React")
                .link("link")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/Certification")
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
    public void itShouldDeleteUserCertification() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Certification certification = profileService.addCertification(dummyUser.getId(),
                CertificationRequestDTO.builder()
                        .name("React")
                        .link("link")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/Certification/"+certification.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getCertifications(dummyUser.getId()));
        assertTrue(profileService.getCertifications(dummyUser.getId()).isEmpty());
    }


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
                        get("/api/v1/profile/Education")
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
        assertEquals(response.get(0).getStartDate(),expectedResponse.get(0).getStartDate());
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
                        post("/api/v1/profile/Education")
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
                        delete("/api/v1/profile/Education/"+education.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getEducations(dummyUser.getId()));
        assertTrue(profileService.getEducations(dummyUser.getId()).isEmpty());
    }

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
                        get("/api/v1/profile/Experience")
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
        assertEquals(response.get(0).getStartDate(),expectedResponse.get(0).getStartDate());
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
                        post("/api/v1/profile/Experience")
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
        assertEquals(response.getStartDate(),expectedResponse.getStartDate());
        assertEquals(response.getCompanyName(),expectedResponse.getCompanyName());


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
                        delete("/api/v1/profile/Experience/"+experience.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getExperiences(dummyUser.getId()));
        assertTrue(profileService.getExperiences(dummyUser.getId()).isEmpty());
    }


    @Test
    public void itShouldGetProjectList() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        profileService.addProject(dummyUser.getId(),
                ProjectRequestDTO.builder()
                        .name("React")
                        .link("link")
                        .description("big description")
                        .build());
        List<Project> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                Project.builder()
                        .name("React")
                        .link("link")
                        .description("big description")
                        .build());

        //request
        String responseJson = api.perform(
                        get("/api/v1/profile/Project")
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
                .link("link")
                .description("big description")
                .build();
        //request

        ProjectRequestDTO payload = ProjectRequestDTO.builder()
                .name("React")
                .link("link")
                .description("big description")
                .build();
        String JsonPayload = objectMapper.writeValueAsString(payload);

        String responseJson = api.perform(
                        post("/api/v1/profile/Project")
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
    public void itShouldDeleteUserProject() throws Exception{
        //setup
        var dummyUser = this.authenticateAsUser();
        Project project = profileService.addProject(dummyUser.getId(),
                ProjectRequestDTO.builder()
                        .name("React")
                        .link("link")
                        .description("big description")
                        .build());

        //request
        api.perform(
                        delete("/api/v1/profile/Project/"+project.getId())
                )
                .andExpect(status().isNoContent());
        //assertions
        //System.out.println("->>>>"+profileService.getProjects(dummyUser.getId()));
        assertTrue(profileService.getProjects(dummyUser.getId()).isEmpty());
    }



}