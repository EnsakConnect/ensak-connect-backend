package com.ensak.connect.chat_message.service;

import com.ensak.connect.chat_message.dto.ChatMessageRequestDTO;
import com.ensak.connect.chat_message.model.ChatMessage;
import com.ensak.connect.chat_message.repository.ChatMessageRepository;
import com.ensak.connect.conversation.model.Conversation;
import com.ensak.connect.conversation.service.ConversationService;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.model.Profile;
import com.ensak.connect.util.push_notification.PushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ConversationService conversationService;
    private final ProfileService profileService;

    // get only the chatMessages within one conversation
    public List<ChatMessage> getChatMessages(Integer conversationId) {
        Conversation conversation = conversationService.getConversationById(conversationId);

        List<ChatMessage> chatMessages = chatMessageRepository.findAll();
        chatMessages.removeIf(chatMessage ->
                !chatMessage.getConversationId().equals(conversation.getId()));

        return chatMessages;
    }

    public ChatMessage createChatMessage(ChatMessageRequestDTO request) {
        // send notification
        Profile profile = profileService.getUserProfileById(request.getSenderId());
        Conversation conversation = conversationService.getConversationById(request.getConversationId());
        int receiverId = conversation.getSender().getId().equals(profile.getId()) ?
                conversation.getReceiver().getId() : conversation.getSender().getId();

        PushNotification.sendChatNotificationToUser("New message from " + profile.getFullName(),
                request.getContent(),
                request.getSenderId(),
                receiverId,
                request.getConversationId());

        // save the message
        return chatMessageRepository.save(
                ChatMessage.builder()
                        .conversationId(request.getConversationId())
                        .senderId(request.getSenderId())
                        .content(request.getContent())
                        .build()
        );
    }

    public ChatMessage getLastChatMessage(Integer conversationId) {
        Conversation conversation = conversationService.getConversationById(conversationId);

        List<ChatMessage> chatMessages = chatMessageRepository.findAll();
        chatMessages.removeIf(chatMessage ->
                !chatMessage.getConversationId().equals(conversation.getId()));

        if (chatMessages.isEmpty()) {
            return null;
        }
        return chatMessages.get(chatMessages.size() - 1);
    }
}
