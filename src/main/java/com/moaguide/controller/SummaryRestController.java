package com.moaguide.controller;

import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.service.DivideService;
import com.moaguide.service.ReportService;
import com.moaguide.service.SummaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/summary/")
@Slf4j
public class SummaryRestController {

    private final SummaryService summaryService;
    private final ReportService reportService;

    // 최근 배당금 + 주목 상품현황
    @GetMapping("/recent/{category}")
    public ResponseEntity<Object> summaryRecent(@PathVariable("category") String category,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        // 최근 배당금 발표
        List<SummaryDivideCustomDto> divideList = summaryService.getDivide(page, size, category);
        // 주목 상품현황
        List<SummaryCustomDto> summaryList = summaryService.getSummary(page, size, category);
        // 최근 배당금 + 주목 상품현황
        SummaryRecentDto summaryRecentDto = new SummaryRecentDto(divideList, summaryList);
        return ResponseEntity.ok(summaryRecentDto);
    }

    // 카테고리별 상품현황 목록 조회
    @GetMapping("/list/{category}")
    public ResponseEntity<Object> summary(@PathVariable("category") String category,
                                          @RequestParam String sort,
                                          @RequestParam int page,
                                          @RequestParam int size) {
        log.info("category: " + category);
        Page<SummaryCustomDto> summary;
        if(sort.equals("views")) {
            summary = summaryService.getSummaryView(page,size, category);
            return ResponseEntity.ok(summary);
        } else if(sort.equals("name")) {
            summary = summaryService.getSummaryName(page,size, category);
            return ResponseEntity.ok(summary);
        } else if(sort.equals("divide")) {
            summary = summaryService.getSummaryDvide(page,size, category);
            return ResponseEntity.ok(summary);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    // 관련 리포트
    @GetMapping("/report/{category}")
    public ResponseEntity<Object> summaryReport(@PathVariable("category") String category,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ReportCustomDto> reportList = reportService.getSummary(category, pageable);
        return ResponseEntity.ok(reportList);
    }

}