package com.ensak.connect.notification.dto;

import com.ensak.connect.auth.dto.UserResponseDTO;
import com.ensak.connect.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {

    private Integer id;
    private String title;
    private String category;
    private String message;
    private String status;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static NotificationResponseDTO map(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .category(notification.getCategory())
                .status(notification.getStatus())
                .author(UserResponseDTO.map(notification.getAuthor()))
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }

    public static List<NotificationResponseDTO> map(List<Notification> notifications) {
        if (notifications == null) {
            return null;
        }

        List<NotificationResponseDTO> list = new ArrayList<NotificationResponseDTO>(notifications.size());
        for (Notification notification : notifications) {
            list.add(map(notification));
        }

        return list;
    }
}
