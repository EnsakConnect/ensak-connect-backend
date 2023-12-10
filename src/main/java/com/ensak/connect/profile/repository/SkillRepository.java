package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    public Optional<List<Skill>> findAllByProfileId(Integer profileId);

    Optional<Skill> findByProfileIdAndId(Integer profileId, Integer skillId);
}
