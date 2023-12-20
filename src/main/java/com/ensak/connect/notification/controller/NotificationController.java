package com.ensak.connect.notification.controller;


import com.ensak.connect.notification.service.NotificationService;
import com.ensak.connect.notification.dto.NotificationRequestDTO;
import com.ensak.connect.notification.dto.NotificationResponseDTO;
import com.ensak.connect.notification.model.Notification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(
            @RequestBody @Valid NotificationRequestDTO request
    ) {
        Notification notification = notificationService.createNotification(request);
        return new ResponseEntity<>(NotificationResponseDTO.map(notification), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll() {
        List<Notification> notifications = notificationService.getNotifications();
        return ResponseEntity.ok(NotificationResponseDTO.map(notifications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> show(@PathVariable Integer id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(NotificationResponseDTO.map(notification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> update(@PathVariable Integer id, @RequestBody @Valid NotificationRequestDTO request) {
        Notification notification = notificationService.updateNotificationById(id, request);
        return new ResponseEntity<>(NotificationResponseDTO.map(notification), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok(null);
    }
}
