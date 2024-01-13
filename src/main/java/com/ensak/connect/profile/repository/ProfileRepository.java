package com.ensak.connect.profile.repository;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.backoffice.dto.DashboardSingleObjectDTO;
import com.ensak.connect.profile.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer>, PagingAndSortingRepository<Profile, Integer> {
    public Optional<Profile> findProfileByUserId(Integer user_id);

    @Query("SELECT p FROM Profile p WHERE LOWER(p.fullName)  LIKE LOWER(:fullname) ORDER BY p.fullName ASC")
    Page<Profile> findUsersByFullName(@Param("fullname") String fullname, Pageable pageable);

    @Query("SELECT p.profileType as field, COUNT(p) as count FROM Profile p GROUP BY p.profileType")
    List<Object[]> countAllByProfileType();

}
