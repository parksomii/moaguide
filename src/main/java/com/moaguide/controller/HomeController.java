package com.moaguide.controller;

import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.ReportAndNewsDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {
    private final NewsService newsService;
    private final ReportService reportService;
    private final SummaryService summaryService;


    // 주요 상품 현황
    @GetMapping("summary/{category}")
    public ResponseEntity<Object> summary(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "3") int size,
                                          @PathVariable String category) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<SummaryCustomDto> summary = summaryService.getSummary(pageable, category);
        return ResponseEntity.ok(summary);
    }

    // 상품현황 북마크 처리
    @PostMapping("summary/{category}/{productId}/BkMark")
    public String addBookMark (@PathVariable String category, @PathVariable String productId, @RequestHeader("Authorization") String token) {
        // 북마크 추가
        return null;
    }
    //페이지 1 사이즈 3
    @GetMapping
    public ResponseEntity<Object> home(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ReportCustomDto> mainReport = reportService.getMainReport(pageable);
        List<NewsCustomDto> mainNews = newsService.getMainNews(pageable);
        ReportAndNewsDto reportAndNewsDto = new ReportAndNewsDto(mainReport, mainNews);
        return ResponseEntity.ok(reportAndNewsDto);
    }

}
