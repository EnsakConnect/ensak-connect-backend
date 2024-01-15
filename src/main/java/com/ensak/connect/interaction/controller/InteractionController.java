package com.ensak.connect.interaction.controller;

import com.ensak.connect.interaction.dto.InteractionResponseDTO;
import com.ensak.connect.interaction.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @GetMapping("/answer/{id}/up")
    public ResponseEntity<InteractionResponseDTO> likeJobPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(InteractionResponseDTO.builder().message(interactionService.interactAnswerUp(id)).build(), HttpStatus.OK);
    }

    @GetMapping("/answer/{id}/down")
    public ResponseEntity<InteractionResponseDTO> dislikeJobPost (
            @PathVariable Integer id
    ) {

        return new ResponseEntity<>(InteractionResponseDTO.builder().message(interactionService.interactAnswerDown(id)).build(), HttpStatus.OK);
    }

}
