package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Integer> {
}
