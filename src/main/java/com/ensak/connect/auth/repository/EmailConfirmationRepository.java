package com.ensak.connect.auth.repository;

import com.ensak.connect.auth.model.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Integer> {
    public Optional<EmailConfirmation> findByEmailAndCode(String email, String code);
    public void deleteByEmail(String email);
    public Optional<EmailConfirmation> findOneByEmail(String email);
}
