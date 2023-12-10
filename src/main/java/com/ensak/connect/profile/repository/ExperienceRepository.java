package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Optional<List<Experience>> findAllByProfileId(Integer profileId);

    Optional<Experience> findByProfileIdAndId(Integer profileId, Integer experienceId);
}
