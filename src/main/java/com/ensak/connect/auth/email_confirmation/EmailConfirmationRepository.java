package com.ensak.connect.auth.email_confirmation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Integer> {

}
