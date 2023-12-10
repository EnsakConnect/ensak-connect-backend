package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<List<Project>> findAllByProfileId(Integer profileId);

    Optional<Project> findByProfileIdAndId(Integer profileId, Integer projectId);
}
