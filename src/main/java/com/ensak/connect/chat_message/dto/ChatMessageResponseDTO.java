package com.ensak.connect.chat_message.dto;

import com.ensak.connect.chat_message.model.ChatMessage;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
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
public class ChatMessageResponseDTO {

    private Integer id;
    private Integer conversationId;
    private Integer senderId;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public static ChatMessageResponseDTO map(ChatMessage chatMessage) {
        return ChatMessageResponseDTO.builder()
                .id(chatMessage.getId())
                .conversationId(chatMessage.getConversationId())
                .senderId(chatMessage.getSenderId())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .updatedAt(chatMessage.getUpdatedAt())
                .build();
    }

    public static List<ChatMessageResponseDTO> map(List<ChatMessage> chatMessages) {
        if (chatMessages == null) {
            return null;
        }

        List<ChatMessageResponseDTO> list = new ArrayList<ChatMessageResponseDTO>(chatMessages.size());
        for (ChatMessage chatMessage : chatMessages) {
            list.add(map(chatMessage));
        }

        return list;
    }
}
