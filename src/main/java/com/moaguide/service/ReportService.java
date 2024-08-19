package com.moaguide.service;

import com.moaguide.domain.report.Report;
import com.moaguide.domain.report.ReportRepository;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.dto.PageRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;

    // 디테일 리포트
    public Page<ReportCustomDto> findBylist(String category, String subCategory, Pageable pageable) {
        Page<ReportCustomDto> reportCustomDtos = reportRepository.findBydetail(category,subCategory,pageable);
        return reportCustomDtos;
    }

    // 메인 리포트
    public List<ReportCustomDto> getMainReport(Pageable pageable) {
        List<ReportCustomDto> reportCustomDtos = reportRepository.findLatest(pageable);
        return reportCustomDtos;
    }

    // 요약 관련 리포트
    public List<ReportCustomDto> getSummary(String category, Pageable pageable) {
        List<ReportCustomDto> reportCustomDtos = reportRepository.findCategoryLatest(category, pageable);
        return reportCustomDtos;
    }

    // 리포트 조회수
    public Report findById(int reportId) {
        return reportRepository.findById(reportId);
    }

    // 리포트 상세
    public ReportCustomDto getReportDetail(int reportId) {
        return reportRepository.findReportDetail(reportId);
    }

    // 인기순
    public List<ReportCustomDto> getAllPopularBySubCategory(String category, String subcategory, int page, int size) {
        if (category.equals("all")) {
            return reportRepository.findAllByViews(subcategory, PageRequest.of(page - 1, size));
        }
        return reportRepository.findListByAllbyViews(category, subcategory, PageRequest.of(page - 1, size));
    }

    // 최신순
    public List<ReportCustomDto> getAllLatestBySubCategory(String category, String subcategory, int page, int size) {
        if (category.equals("all")) {
            return reportRepository.findAllbyLatest(subcategory, PageRequest.of(page - 1, size, Sort.by("date").descending()));
        }
        return reportRepository.findListByAllbyLatest(category, subcategory, PageRequest.of(page - 1, size, Sort.by("date").descending()));
    }
}
