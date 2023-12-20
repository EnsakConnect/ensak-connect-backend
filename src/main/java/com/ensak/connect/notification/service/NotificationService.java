package com.ensak.connect.notification.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.notification.dto.NotificationRequestDTO;
import com.ensak.connect.notification.model.Notification;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthenticationService authenticationService;

    public Notification getNotificationById(Integer id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find notification with id " + id + ".")
        );
    }

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification createNotification(NotificationRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return notificationRepository.save(
                Notification.builder()
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .author(author)
                        .status(request.getStatus())
                        .category(request.getCategory())
                        .build()
        );
    }

    @Transactional
    public Notification updateNotificationById(Integer id, NotificationRequestDTO request) {

        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find notification with id " + id + ".")
        );

        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setStatus(request.getStatus());
        notification.setCategory(request.getCategory());

        return notificationRepository.save(notification);
    }

    @SneakyThrows
    public void deleteNotificationById(Integer id) {

        User author = authenticationService.getAuthenticatedUser();
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find notification with id " + id + ".")
        );

        if (!author.getId().equals(notification.getAuthor().getId())) {
            throw new ForbiddenException("Cannot delete notification made by other users");
        }

        notificationRepository.deleteById(id);
    }
}
