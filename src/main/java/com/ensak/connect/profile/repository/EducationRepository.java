package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<List<Education>> findAllByProfileId(Integer profileId);

    Optional<Education> findByProfileIdAndId(Integer profileId, Integer educationId);
}
