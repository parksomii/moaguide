package com.moaguide.controller;

import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.ReportAndNewsDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.dto.SearchRankDto;
import com.moaguide.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<Object> summary(@PathVariable("category") String category,
                                          @RequestParam String sort,
                                          @RequestParam int page, @RequestParam int size) {
        log.info("category: " + category);
        if(sort.equals("views")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryView(page,size, category);
            return ResponseEntity.ok(summary);
        } else if(sort.equals("name")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryName(page,size, category);
            return ResponseEntity.ok(summary);
        } else if(sort.equals("divide")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryDvide(page,size, category);
            return ResponseEntity.ok(summary);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    // 상품현황 북마크 처리
    @PostMapping("summary/{category}/{productId}/BkMark")
    public String addBookMark (@PathVariable("category") String category, @PathVariable("productId") String productId, @RequestHeader("Authorization") String token) {
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

    // 검색 순위
    @GetMapping("searchRank")
    public ResponseEntity<Object> searchRank() {
        List<String> names = Arrays.asList(
                "소액 부동산 투자",
                "롯데월드타워 청약",
                "부동산 투자 가이드",
                "한우 투자",
                "소액 투자 방법"
        );

        List<SearchRankDto> searchRank = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            searchRank.add(new SearchRankDto(names.get(i), i + 1));
        }
        return ResponseEntity.ok(searchRank);
    }
}
