package com.moaguide.controller;


import com.moaguide.domain.study.Roadmap;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.RoadmapResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import com.moaguide.dto.NewDto.customDto.SubRoadmapDto;
import com.moaguide.service.StudyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/study/")
public class ArticleRestController {
    private final StudyService studyService;

    @GetMapping("guide")
    public ResponseEntity<RoadmapResponseDto> getroadmap(@RequestParam int page , @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Roadmap> roadmaps = studyService.findAll(pageable);
        return ResponseEntity.ok(new RoadmapResponseDto(roadmaps));
    }

    @GetMapping("article")
    public ResponseEntity<ArticlelistResponseDto> getarticle(@RequestParam(defaultValue = "999999") int nextCursor) {
        ArticlelistResponseDto articleDtos = studyService.findAllByarticle(nextCursor);
        return ResponseEntity.ok(articleDtos);
    }

    @GetMapping("article/roadmap/{id}")
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable int id) {
        List<ArticleSummaryDto> articleDtos = studyService.findByRoadmap(id);
        Map<String, Object> response = new HashMap<>();
        response.put("articleList", articleDtos);
        return ResponseEntity.ok(response);
    }
}
