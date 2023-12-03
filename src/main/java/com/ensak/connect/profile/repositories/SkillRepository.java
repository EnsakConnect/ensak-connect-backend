package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
