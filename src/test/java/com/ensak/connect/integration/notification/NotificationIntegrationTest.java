package com.ensak.connect.integration.notification;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.ensak.connect.notification.dto.NotificationRequestDTO;
import com.ensak.connect.notification.dto.NotificationResponseDTO;
import com.ensak.connect.notification.model.Notification;
import com.ensak.connect.notification.repository.NotificationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationIntegrationTest extends AuthenticatedBaseIntegrationTest {


    public static final String API_NOTIFICATIONS = "/api/v1/notifications";
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
    }

    NotificationRequestDTO notificationTest = NotificationRequestDTO.builder()
            .title("This is a job title test")
            .message("This is a job notification message test")
            .status("This is the status test")
            .category("This is the category test")
            .build();


    @Test
    @Transactional
    public void itShouldCreateNotificationWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString(notificationTest);
        String response = api.perform(
                        post(API_NOTIFICATIONS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }


    @Test
    public void itShouldNotCreateNotificationWhenNotAuthenticated() throws Exception {
        String requestJSON = objectMapper.writeValueAsString(notificationTest);
        String response = api.perform(
                        post(API_NOTIFICATIONS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void isShouldGetNotificationWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_NOTIFICATIONS + "/" + notification.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        NotificationResponseDTO notificationResponseDTO = objectMapper.readValue(response, NotificationResponseDTO.class);

        Assertions.assertEquals(notification.getId(), notificationResponseDTO.getId());
        Assertions.assertEquals(notification.getTitle(), notificationResponseDTO.getTitle());
        Assertions.assertEquals(notification.getMessage(), notificationResponseDTO.getMessage());
        Assertions.assertEquals(notification.getStatus(), notificationResponseDTO.getStatus());
        Assertions.assertEquals(notification.getCategory(), notificationResponseDTO.getCategory());
        Assertions.assertEquals(notification.getAuthor().getId(), notificationResponseDTO.getAuthor().getUserId());

    }

    @Test
    public void isShouldNotGetNotificationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_NOTIFICATIONS + "/" + notification.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetListNotificationWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var notification1 = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle() + "1")
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );
        var notification2 = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle() + "2")
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );
        var notification3 = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle() + "3")
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );


        String response = api.perform(
                        get(API_NOTIFICATIONS)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<NotificationResponseDTO> notifications = objectMapper.readValue(response, new TypeReference<List<NotificationResponseDTO>>() {
        });
        Assertions.assertNotNull(notifications);
        NotificationResponseDTO notificationTest1 = notifications.get(0);
        NotificationResponseDTO notificationTest2 = notifications.get(1);
        NotificationResponseDTO notificationTest3 = notifications.get(2);

        Assertions.assertEquals(notification1.getId(), notificationTest1.getId());
        Assertions.assertEquals(notification1.getTitle(), notificationTest1.getTitle());
        Assertions.assertEquals(notification1.getMessage(), notificationTest1.getMessage());
        Assertions.assertEquals(notification1.getStatus(), notificationTest1.getStatus());
        Assertions.assertEquals(notification1.getCategory(), notificationTest1.getCategory());
        Assertions.assertEquals(notification1.getAuthor().getId(), notificationTest1.getAuthor().getUserId());

        Assertions.assertEquals(notification2.getId(), notificationTest2.getId());
        Assertions.assertEquals(notification2.getTitle(), notificationTest2.getTitle());
        Assertions.assertEquals(notification2.getMessage(), notificationTest2.getMessage());
        Assertions.assertEquals(notification2.getStatus(), notificationTest2.getStatus());
        Assertions.assertEquals(notification2.getCategory(), notificationTest2.getCategory());
        Assertions.assertEquals(notification2.getAuthor().getId(), notificationTest2.getAuthor().getUserId());

        Assertions.assertEquals(notification3.getId(), notificationTest3.getId());
        Assertions.assertEquals(notification3.getTitle(), notificationTest3.getTitle());
        Assertions.assertEquals(notification3.getMessage(), notificationTest3.getMessage());
        Assertions.assertEquals(notification3.getStatus(), notificationTest3.getStatus());
        Assertions.assertEquals(notification3.getCategory(), notificationTest3.getCategory());
        Assertions.assertEquals(notification3.getAuthor().getId(), notificationTest3.getAuthor().getUserId());

    }

    @Test
    public void isShouldNotGetListNotificationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_NOTIFICATIONS)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldUpdateNotificationWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        var notificationUpdated = NotificationRequestDTO.builder()
                .title(notificationTest.getTitle() + " updated")
                .message(notificationTest.getMessage() + " updated")
                .status(notificationTest.getStatus() + " updated")
                .category(notificationTest.getCategory() + " updated")
                .build();

        String requestJSON = objectMapper.writeValueAsString(notificationUpdated);

        String response = api.perform(
                        put(API_NOTIFICATIONS + "/" + notification.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();
        NotificationResponseDTO notificationResponseDTO = objectMapper.readValue(response, NotificationResponseDTO.class);

        Assertions.assertEquals(notification.getId(), notificationResponseDTO.getId());
        Assertions.assertNotEquals(notification.getTitle(), notificationResponseDTO.getTitle());
        Assertions.assertNotEquals(notification.getMessage(), notificationResponseDTO.getMessage());
        Assertions.assertNotEquals(notification.getStatus(), notificationResponseDTO.getStatus());
        Assertions.assertNotEquals(notification.getCategory(), notificationResponseDTO.getCategory());
        Assertions.assertEquals(notification.getAuthor().getId(), notificationResponseDTO.getAuthor().getUserId());
        Assertions.assertEquals(notificationUpdated.getTitle(), notificationResponseDTO.getTitle());
        Assertions.assertEquals(notificationUpdated.getMessage(), notificationResponseDTO.getMessage());
        Assertions.assertEquals(notificationUpdated.getStatus(), notificationResponseDTO.getStatus());
        Assertions.assertEquals(notificationUpdated.getCategory(), notificationResponseDTO.getCategory());

    }

    @Test
    public void isShouldNotUpdateNotificationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        var notificationUpdated = NotificationRequestDTO.builder()
                .title(notificationTest.getTitle() + " updated")
                .message(notificationTest.getMessage() + " updated")
                .status(notificationTest.getStatus() + " updated")
                .category(notificationTest.getCategory() + " updated")
                .build();

        String requestJSON = objectMapper.writeValueAsString(notificationUpdated);

        String response = api.perform(
                        put(API_NOTIFICATIONS + "/" + notification.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldAllowUserToDeleteHisOwnNotificationWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_NOTIFICATIONS + "/" + notification.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteHisOwnNotificationWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_NOTIFICATIONS + "/" + notification.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteNotificationMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        var notification = notificationRepository.save(
                Notification.builder()
                        .title(notificationTest.getTitle())
                        .message(notificationTest.getMessage())
                        .status(notificationTest.getStatus())
                        .category(notificationTest.getCategory())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        delete(API_NOTIFICATIONS + "/" + notification.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse apiResponse = objectMapper.readValue(response, HttpResponse.class);

        Assertions.assertEquals("Cannot delete notification made by other users", apiResponse.getMessage());
    }
}
