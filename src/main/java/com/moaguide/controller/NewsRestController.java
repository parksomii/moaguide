package com.moaguide.controller;


import com.moaguide.domain.news.News;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.NewsService;
import com.moaguide.service.view.NewsViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/news/")
@AllArgsConstructor
@Slf4j
public class NewsRestController {
    private final NewsService newsService;
    private final JWTUtil jwtUtil;

    // 뉴스 홈페이지
    @GetMapping()
    public ResponseEntity<List<NewsCustomDto>> newsHome() {
        List<NewsCustomDto> newsData = newsService.findNews();
        return ResponseEntity.ok().body(newsData);
    }

    // 카테고리별 뉴스 조회
    @GetMapping("/{category}")
    public ResponseEntity<?> newsListByCategory(@PathVariable String category,
                                                @RequestParam String sort,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        Page<NewsCustomDto> newsList;
        // 인기순
        if (sort.equals("popular")) {
            newsList = newsService.getAllByViews(page, size, category);
        }
        // 최신순
        else {
            newsList = newsService.getAllByLatest(page, size, category);
        }
        return ResponseEntity.ok().body(newsList);
    }



}
