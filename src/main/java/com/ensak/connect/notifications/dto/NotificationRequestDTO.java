package com.ensak.connect.notifications.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {

    @NotBlank(message = "Job title should not be blank")
    private String title;

    @NotBlank(message = "Message should not be blank")
    private String message;

    @NotBlank(message = "Status name should not be blank")
    private String status;

    @NotBlank(message = "Category should not be blank")
    private String category;
}
