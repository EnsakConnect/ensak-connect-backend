package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<List<Language>> findAllByProfileId(Integer profileId);

    Optional<Language> findByProfileIdAndId(Integer profileId, Integer languageId);
}
