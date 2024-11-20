package com.moaguide.controller;

import com.moaguide.dto.ContentDto;
import com.moaguide.service.ArticleContentService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/contents")
@Slf4j
public class ContentController {

    private final ArticleContentService articleContentService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getContentsByCategory(
            @RequestParam int categoryId,
            @RequestParam int page) {
        Page<ContentDto> contents = articleContentService.getContentsByCategory(categoryId, page);
        Map<String, Object> response = new HashMap<>();
        response.put("content", contents.getContent()); // 현재 페이지 데이터
        response.put("page", page);                     // 현재 페이지 번호
        response.put("size", contents.getSize());       // 한 페이지당 데이터 개수
        response.put("total", contents.getTotalElements()); // 전체 데이터 개수
        response.put("category", categoryId);
        return ResponseEntity.ok(response);
    }
}

