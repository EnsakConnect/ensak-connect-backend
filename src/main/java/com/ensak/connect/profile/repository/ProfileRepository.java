package com.ensak.connect.profile.repository;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Optional<Profile> findProfileByUserId(Integer user_id);

    @Query("SELECT p FROM Profile p WHERE p.fullName LIKE :fullname")
    List<Profile> findUsersByFullName(@Param("fullname") String fullname);
}
