package com.ensak.connect.chat_message.service;

import com.ensak.connect.chat_message.dto.ChatMessageRequestDTO;
import com.ensak.connect.chat_message.model.ChatMessage;
import com.ensak.connect.chat_message.repository.ChatMessageRepository;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.conversation.model.Conversation;
import com.ensak.connect.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ConversationService conversationService;

    public ChatMessage getChatMessageById(Integer id) {
        return chatMessageRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find chatMessage with id " + id + ".")
        );
    }

    // get only the chatMessages within one conversation
    public List<ChatMessage> getChatMessages(Integer conversationId) {
        Conversation conversation = conversationService.getConversationById(conversationId);

        List<ChatMessage> chatMessages = chatMessageRepository.findAll();
        chatMessages.removeIf(chatMessage ->
                !chatMessage.getConversationId().equals(conversation.getId()));

        return chatMessages;
    }

    public ChatMessage createChatMessage(ChatMessageRequestDTO request) {
        return chatMessageRepository.save(
                ChatMessage.builder()
                        .conversationId(request.getConversationId())
                        .senderId(request.getSenderId())
                        .content(request.getContent())
                        .build()
        );
    }
}
