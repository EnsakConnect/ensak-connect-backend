package com.ensak.connect.interaction.repository;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.interaction.model.Interaction;
import com.ensak.connect.question_post.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Integer> {

    Interaction findByAuthorAndAnswer(User user, Answer answer);
}
