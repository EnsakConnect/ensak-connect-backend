package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    Optional<List<Certification>> findAllByProfileId(Integer profileId);
}
