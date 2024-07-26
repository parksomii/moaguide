package com.moaguide.controller;

import com.moaguide.dto.NewDto.DetailReportResponseDto;
import com.moaguide.dto.NewDto.customDto.DetailNewsResponseDto;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.service.NewsService;
import com.moaguide.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/")
public class DetailRestcontroller {
    private ReportService reportService;
    private NewsService newsService;

    @GetMapping("report/{category}")
    public ResponseEntity<Object> report(@PathVariable String category,@RequestParam int page,@RequestParam int size,@RequestParam String subCategory) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ReportCustomDto> Report = reportService.findBylist(category,subCategory,pageable);
        return ResponseEntity.ok(new DetailReportResponseDto(Report));
    }

    @GetMapping("news/{category}")
    public ResponseEntity<Object> news(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<NewsCustomDto> news = newsService.findBydetail(keyword,pageable);
        log.info("뉴스체크 :{}", news);
        return ResponseEntity.ok(new DetailNewsResponseDto(news));
    }
}
