package com.ensak.connect.chat_message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDTO {

    @NotNull(message = "conversationId should not be null")
    private Integer conversationId;

    @NotNull(message = "senderId should not be null")
    private Integer senderId;

    @NotBlank(message = "content should not be blank")
    private String content;
}
