package com.moaguide.controller;

import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.ReportAndNewsDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.service.NewsService;
import com.moaguide.service.ReportService;
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
