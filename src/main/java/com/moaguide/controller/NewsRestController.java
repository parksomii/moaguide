package com.moaguide.controller;


import com.moaguide.domain.news.News;
import com.moaguide.domain.summary.Summary;
import com.moaguide.service.NewsService;
import com.moaguide.service.view.NewsViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/news/")
@AllArgsConstructor
@Slf4j
public class NewsRestController {
    private final NewsService newsService;
    private final NewsViewService newsViewService;

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
