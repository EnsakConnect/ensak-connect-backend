package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<List<Language>> findAllByProfileId(Integer profileId);
}
