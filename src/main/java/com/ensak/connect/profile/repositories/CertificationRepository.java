package com.ensak.connect.profile.repositories;

import com.ensak.connect.profile.models.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
}
