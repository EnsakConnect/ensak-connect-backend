package com.ensak.connect.conversation.dto;

import com.ensak.connect.conversation.model.Conversation;
import com.ensak.connect.profile.model.Profile;
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
public class ConversationResponseDTO {

    private Integer id;
    private Profile sender;
    private Profile receiver;
    private String lastMessage;
    private Date lastMessageDate;
    private Date createdAt;
    private Date updatedAt;

    public static ConversationResponseDTO map(Conversation conversation) {
        return ConversationResponseDTO.builder()
                .id(conversation.getId())
                .sender(conversation.getSender())
                .receiver(conversation.getReceiver())
                .lastMessage(conversation.getLastMessage())
                .lastMessageDate(conversation.getLastMessageDate())
                .createdAt(conversation.getCreatedAt())
                .updatedAt(conversation.getUpdatedAt())
                .build();
    }

    public static List<ConversationResponseDTO> map(List<Conversation> conversations) {
        if (conversations == null) {
            return null;
        }

        List<ConversationResponseDTO> list = new ArrayList<ConversationResponseDTO>(conversations.size());
        for (Conversation conversation : conversations) {
            list.add(map(conversation));
        }

        return list;
    }
}
