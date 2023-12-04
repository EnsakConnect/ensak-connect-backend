package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Profile;
import com.ensak.connect.profile.models.Skill;
import com.ensak.connect.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Optional<Profile> findProfileByUserId(Integer user_id);
}
