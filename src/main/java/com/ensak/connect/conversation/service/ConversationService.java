package com.ensak.connect.conversation.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.config.exception.model.UserNotFoundException;
import com.ensak.connect.conversation.dto.ConversationRequestDTO;
import com.ensak.connect.conversation.model.Conversation;
import com.ensak.connect.conversation.repository.ConversationRepository;
import com.ensak.connect.profile.ProfileService;
import com.ensak.connect.profile.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;

    public Conversation getConversationById(Integer id) {
        return conversationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find conversation with id " + id + ".")
        );
    }

    // Return only the auth user conversations
    public List<Conversation> getConversations() {
        User user = authenticationService.getAuthenticatedUser();
        Profile profile = user.getProfile();

        List<Conversation> conversations = conversationRepository.findAll();
        conversations.removeIf(conversation -> !conversation.getSender().getId().equals(profile.getId()) &&
                !conversation.getReceiver().getId().equals(profile.getId()));

        return conversations;
    }

    public Conversation createConversation(ConversationRequestDTO request) {
        try {
            Profile sender = profileService.getUserProfileById(request.getSenderId());
            Profile receiver = profileService.getUserProfileById(request.getReceiverId());
            return conversationRepository.save(
                    Conversation.builder()
                            .sender(sender)
                            .receiver(receiver)
                            .build()
            );
        } catch (Exception e) {
            throw new NotFoundException("Could not create conversation");
        }
    }
}
