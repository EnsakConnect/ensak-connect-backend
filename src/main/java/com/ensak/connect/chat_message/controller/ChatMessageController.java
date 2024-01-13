package com.ensak.connect.chat_message.controller;


import com.ensak.connect.chat_message.dto.ChatMessageRequestDTO;
import com.ensak.connect.chat_message.dto.ChatMessageResponseDTO;
import com.ensak.connect.chat_message.model.ChatMessage;
import com.ensak.connect.chat_message.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ChatMessageResponseDTO> create(
            @RequestBody @Valid ChatMessageRequestDTO request
    ) {
        ChatMessage chatMessage = chatMessageService.createChatMessage(request);
        return new ResponseEntity<>(ChatMessageResponseDTO.map(chatMessage), HttpStatus.CREATED);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> getAll(
            @PathVariable Integer conversationId
    ) {
        List<ChatMessage> chatMessages = chatMessageService.getChatMessages(conversationId);
        return ResponseEntity.ok(ChatMessageResponseDTO.map(chatMessages));
    }
}
