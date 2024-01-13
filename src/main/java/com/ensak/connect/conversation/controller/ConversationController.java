package com.ensak.connect.conversation.controller;


import com.ensak.connect.chat_message.model.ChatMessage;
import com.ensak.connect.chat_message.service.ChatMessageService;
import com.ensak.connect.conversation.dto.ConversationRequestDTO;
import com.ensak.connect.conversation.dto.ConversationResponseDTO;
import com.ensak.connect.conversation.model.Conversation;
import com.ensak.connect.conversation.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ConversationResponseDTO> create(
            @RequestBody @Valid ConversationRequestDTO request
    ) {
        Conversation conversation = conversationService.createConversation(request);
        return new ResponseEntity<>(ConversationResponseDTO.map(conversation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> getAll() {
        List<Conversation> conversations = conversationService.getConversations();
        conversations.forEach(conversation -> {
            ChatMessage lastMessage = chatMessageService.getLastChatMessage(conversation.getId());
            if (lastMessage != null) {
                conversation.setLastMessage(lastMessage.getContent());
                conversation.setLastMessageDate(lastMessage.getCreatedAt());
            }
        });
        return ResponseEntity.ok(ConversationResponseDTO.map(conversations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponseDTO> show(@PathVariable Integer id) {
        Conversation conversation = conversationService.getConversationById(id);
        return ResponseEntity.ok(ConversationResponseDTO.map(conversation));
    }
}
