package com.ensak.connect.unit.notification;

import com.ensak.connect.notifications.controller.NotificationController;
import com.ensak.connect.notifications.dto.NotificationRequestDTO;
import com.ensak.connect.notifications.dto.NotificationResponseDTO;
import com.ensak.connect.notifications.model.Notification;
import com.ensak.connect.notifications.service.NotificationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@Disabled
public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    public void testAddNotification_validRequest_returnsAccepted() throws MethodArgumentNotValidException {
        NotificationRequestDTO request = NotificationRequestDTO.builder()
                .title("job title")
                .message("message")
                .status("status")
                .category("categoty")
                .build();


        Notification notification = Notification.builder()
                .id(1)
                .title("job title")
                .message("message")
                .status("status")
                .category("categoty")
                .build();
        NotificationResponseDTO res = NotificationResponseDTO.builder()
                .id(1)
                .title("job title")
                .message("message")
                .status("status")
                .category("categoty")
                .author(null)
                .build();
        when(notificationService.createNotification(Mockito.any(NotificationRequestDTO.class))).thenReturn(notification);
        when(NotificationResponseDTO.map(Mockito.any(Notification.class))).thenReturn(res);


        ResponseEntity<?> responseEntity = notificationController.create(request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Mockito.verify(notificationService, Mockito.times(1)).createNotification(request);
    }


    @Test
    public void testFindAllNotifications_returnsOk() {
        List<Notification> notifications = new ArrayList<>();
        Notification notification1 = new Notification();
        notification1.setTitle("Software Engineer");
        notification1.setMessage("Develop and maintain software applications.");
        notifications.add(notification1);

        Notification notification2 = new Notification();
        notification2.setTitle("Product Manager");
        notification2.setMessage("Manage the product development lifecycle.");
        notifications.add(notification2);

        when(notificationService.getNotifications()).thenReturn(notifications);

        ResponseEntity<List<NotificationResponseDTO>> responseEntity = notificationController.getAll();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(notifications);
    }
}