package com.ensak.connect.conversation.repository;

import com.ensak.connect.conversation.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

}
