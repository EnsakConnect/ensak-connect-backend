package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    public Optional<List<Skill>> findAllByProfileId(Integer profileId);
}
