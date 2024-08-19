package com.moaguide.domain.report;

import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 메인 주요 리포트
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id,r.title, r.content, r.category,r.date) FROM Report r ORDER BY r.view DESC")
    List<ReportCustomDto> findLatest(Pageable pageable);

    // 카테고리별 최신 리포트
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id,r.title,r.content,r.category,r.date) FROM Report r WHERE r.category = :category ORDER BY r.date DESC")
    List<ReportCustomDto> findCategoryLatest(@Param("category") String category, Pageable pageable);

    // 카테고리별 디테일 리포트 조회
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id,r.title,r.content,r.category,r.date) FROM Report r WHERE r.category = :category AND (r.subCategory = 'situation' OR r.subCategory = 'guide' OR r.subCategory = :subcategory) ORDER BY r.date DESC")
    Page<ReportCustomDto> findBydetail(@Param("category") String category, @Param("subcategory") String subCategory, Pageable pageable);

    // 리포트 조회수
    Report findById(int reportId);

    // 리포트 상세
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id, r.title, r.content, r.category, r.date) " +
            "FROM Report r WHERE r.id = :reportId")
    ReportCustomDto findReportDetail(@Param("reportId") int reportId);

    // 전체 카테고리 인기순
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id, r.title, r.content, r.category, r.date) " +
            "FROM Report r WHERE r.subCategory = :subcategory ORDER BY r.view DESC")
    Page<ReportCustomDto> findAllByViews(@Param("subcategory") String subcategory, Pageable pageable);
    // 카테고리별 인기순
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id, r.title, r.content, r.category, r.date) " +
            "FROM Report r WHERE r.category = :category AND r.subCategory = :subcategory " +
            "ORDER BY r.view DESC")
    Page<ReportCustomDto> findListByAllbyViews(@Param("category") String category, @Param("subcategory") String subcategory, Pageable pageable);

    // 전체 카테고리 최신순
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id, r.title, r.content, r.category, r.date) " +
            "FROM Report r WHERE r.subCategory = :subcategory ORDER BY r.date DESC")
    Page<ReportCustomDto> findAllbyLatest(@Param("subcategory") String subcategory, Pageable pageable);
    // 카테고리별 최신순
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ReportCustomDto(r.id, r.title, r.content, r.category, r.date) " +
            "FROM Report r WHERE r.category = :category AND r.subCategory = :subcategory " +
            "ORDER BY r.date DESC")
    Page<ReportCustomDto> findListByAllbyLatest(@Param("category") String category, @Param("subcategory") String subcategory, Pageable pageable);
}
