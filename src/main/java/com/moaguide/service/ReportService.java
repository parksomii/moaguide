package com.moaguide.service;

import com.moaguide.domain.report.Report;
import com.moaguide.domain.report.ReportRepository;
import com.moaguide.dto.customDto.ReportCustomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;

    // 주요 리포트
    public List<ReportCustomDto> getMainReport(Pageable pageable) {
        Page<Report> reportList = reportRepository.findLatest(pageable);
        List<ReportCustomDto> reportData = reportList.
                stream().
                map(report -> new ReportCustomDto(report))
                .collect(Collectors.toList());
        return reportData;
    }
}
