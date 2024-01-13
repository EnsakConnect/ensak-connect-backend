package com.ensak.connect.auth.repository;

import com.ensak.connect.auth.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY u.updatedAt DESC ")
    Page<User> getUsersPage(Pageable pageable);


}
