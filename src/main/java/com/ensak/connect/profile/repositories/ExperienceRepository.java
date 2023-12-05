package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Optional<List<Experience>> findAllByProfileId(Integer profileId);
}
