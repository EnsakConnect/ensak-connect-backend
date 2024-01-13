package com.ensak.connect.report;

import com.ensak.connect.auth.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportRepository extends JpaRepository<Report, Integer>, PagingAndSortingRepository<Report, Integer> {

    @Query("SELECT r FROM Report r ORDER BY r.updatedAt DESC ")
    Page<Report> getReposrtsPage(Pageable pageable);
}
