package com.moaguide.service;

import com.moaguide.domain.report.ReportRepository;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
