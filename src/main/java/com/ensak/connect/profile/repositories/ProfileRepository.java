package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
