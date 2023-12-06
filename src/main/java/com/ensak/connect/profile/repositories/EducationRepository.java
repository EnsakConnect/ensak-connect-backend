package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<List<Education>> findAllByProfileId(Integer profileId);
}