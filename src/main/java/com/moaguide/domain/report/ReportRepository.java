package com.moaguide.domain.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 최신 리포트
    @Query("SELECT r FROM Report r ORDER BY r.date DESC")
    Page<Report> findLatest(Pageable pageable);
}
