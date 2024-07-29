package com.moaguide.controller;


import com.moaguide.domain.news.News;
import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.service.NewsService;
import com.moaguide.service.view.NewsViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/news/")
@AllArgsConstructor
@Slf4j
public class NewsRestController {
    private final NewsService newsService;
    private final NewsViewService newsViewService;

    // 뉴스 홈페이지
    @GetMapping
    public ResponseEntity<List<NewsCustomDto>> newsHome() {
        List<NewsCustomDto> newsData = newsService.findNews();
        return ResponseEntity.ok().body(newsData);
    }

    // 카테고리별 뉴스 조회
    @GetMapping("/{category}")
    public ResponseEntity<List<NewsCustomDto>> newsListByCategory(@PathVariable String category, @RequestParam String sort, PageRequestDTO pageRequestDTO ) {
        // 최신순
        if (sort.equals("latest")) {
            Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("date").descending());
            Page<News> newsList = newsService.getAllByLatest(pageRequestDTO, category);
            List<NewsCustomDto> newsData = newsList.stream().map(news -> new NewsCustomDto(news)).collect(Collectors.toList());
            return ResponseEntity.ok().body(newsData);
        }
        // 인기순
        else {
            Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("views").descending());
            Page<News> newsList = newsService.getAllByViews(pageRequestDTO);
            List<NewsCustomDto> newsData = newsList.stream().map(news -> new NewsCustomDto(news)).collect(Collectors.toList());
            return ResponseEntity.ok().body(newsData);
        }
    }
    @PostMapping("{news_Id}")
    public ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> detail_check(@PathVariable Long news_Id, @RequestHeader("Local-Storage-Key") String localStorageKey, @RequestHeader("Local-date") String date){
        News news = newsService.findById(news_Id);
        log.info("뉴스 객체 체크 :  {}", news);
        String response = newsViewService.insert(news,localStorageKey,date);
        if(response != null) {
            return ResponseEntity.ok();
        }else{
            return ResponseEntity.badRequest();
        }
    }
}
