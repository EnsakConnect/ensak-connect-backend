package com.ensak.connect.chat_message.repository;

import com.ensak.connect.chat_message.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

}
