package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
