package com.ensak.connect.profile.repository;

import com.ensak.connect.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Optional<Profile> findProfileByUserId(Integer user_id);
}
