package com.moaguide.service;

import com.moaguide.domain.report.Report;
import com.moaguide.domain.view.ReportView;
import com.moaguide.domain.view.ReportViewId;
import com.moaguide.domain.view.ReportViewRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ReportViewService {
    private final ReportViewRepository reportViewRepository;

    @Transactional
    public String insert(Report report, String localStorageKey, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            reportViewRepository.save(new ReportView(new ReportViewId(localStorageKey, localDate, report)));
            return "User saved successfully";
        } catch (DataIntegrityViolationException e) {
            return "Primary key already exists. User not saved.";
        }
    }
}
