package com.ensak.connect.auth.repository;

import com.ensak.connect.auth.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {
    public Optional<PasswordReset> findByEmailAndCode(String email, String code);
    public void deleteByEmail(String email);

    Optional<PasswordReset> findByEmail(String email);
}
