package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<List<Project>> findAllByProfileId(Integer profileId);
}
