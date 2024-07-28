package com.moaguide.controller;


import com.moaguide.domain.news.News;
import com.moaguide.domain.summary.Summary;
import com.moaguide.service.NewsService;
import com.moaguide.service.view.NewsViewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/news")
@AllArgsConstructor
public class NewsRestController {
    private final NewsService newsService;
    private final NewsViewService newsViewService;

    @PostMapping("{news_Id}")
    public ResponseEntity<Object> detail_check(@PathVariable String news_Id, @RequestHeader("Local-Storage-Key") String localStorageKey, @RequestHeader("Local-date") String date){
        News news = newsService.findById(news_Id);
        String response = newsViewService.insert(news,localStorageKey,date);
        return ResponseEntity.ok(response);
    }
}
