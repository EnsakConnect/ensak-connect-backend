package com.ensak.connect.unit.notification;

import com.ensak.connect.notification.dto.NotificationRequestDTO;
import com.ensak.connect.notification.model.Notification;
import com.ensak.connect.notification.repository.NotificationRepository;
import com.ensak.connect.notification.service.NotificationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    public void testSave() {
        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setTitle("Software Engineer");
        request.setMessage("Develop and maintain software applications.");
        notificationService.createNotification(request);

        Notification expectedNotification = new Notification();
        expectedNotification.setTitle("Software Engineer");
        expectedNotification.setMessage("Develop and maintain software applications.");

        Mockito.verify(notificationRepository, Mockito.times(1)).save(expectedNotification);
    }

    @Test
    public void testFindAll() {
        List<Notification> notifications = new ArrayList<>();
        Notification notification1 = new Notification();
        notification1.setTitle("Software Engineer");
        notification1.setMessage("Develop and maintain software applications.");
        notifications.add(notification1);

        Notification notification2 = new Notification();
        notification2.setTitle("Product Manager");
        notification2.setMessage("Manage the product development lifecycle.");
        notifications.add(notification2);

        Mockito.when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> actualNotifications = notificationService.getNotifications();

        assertThat(actualNotifications).isEqualTo(notifications);
    }
}
