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

    public Page<ReportCustomDto> findBylist(String category, String subCategory, Pageable pageable) {
        Page<ReportCustomDto> reportCustomDtos = reportRepository.findBydetail(category,subCategory,pageable);
        return reportCustomDtos;
    }

    public List<ReportCustomDto> getMainReport(Pageable pageable) {
        List<ReportCustomDto> reportCustomDtos = reportRepository.findLatest(pageable);
        return reportCustomDtos;
    }

    public Report findById(int reportId) {
        return reportRepository.findById(reportId);
    }

    // 인기순
    public Page<ReportCustomDto> getAllPopularBySubCategory(String category, String subcategory, Pageable pageable) {
        return reportRepository.findAllByViews(category, subcategory, pageable);
    }

    // 최신순
    public Page<ReportCustomDto> getAllLatestBySubCategory(String category, String subcategory, Pageable pageable) {
        return reportRepository.findAllByLatest(category, subcategory, pageable);
    }
}
