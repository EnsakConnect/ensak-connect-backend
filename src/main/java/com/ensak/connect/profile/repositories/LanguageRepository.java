package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
