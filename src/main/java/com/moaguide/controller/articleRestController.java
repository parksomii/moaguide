package com.moaguide.controller;


import com.moaguide.domain.study.Article;
import com.moaguide.domain.study.Roadmap;
import com.moaguide.domain.study.SubRoadmap;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.RoadmapResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.dto.NewDto.customDto.SubRoadmapDto;
import com.moaguide.service.StudyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/study/")
public class articleRestController {
    private final StudyService studyService;

    @GetMapping("guide")
    public ResponseEntity<RoadmapResponseDto> getroadmap(@RequestParam int page , @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Roadmap> roadmaps = studyService.findAll(pageable);
        return ResponseEntity.ok(new RoadmapResponseDto(roadmaps));
    }

    @GetMapping("article")
    public ResponseEntity<ArticlelistResponseDto> getarticle(@RequestParam int page , @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        ArticlelistResponseDto articleDtos = studyService.findAllByarticle(pageable);
        return ResponseEntity.ok(articleDtos);
    }

        @GetMapping("guide/{category}")
    public ResponseEntity<List<SubRoadmapDto>> getsubroadmap(@RequestParam int category) {
        List<SubRoadmapDto> subRoadmap = studyService.findAllbysub(category);
        return ResponseEntity.ok(subRoadmap);
    }

    @GetMapping("guide/article")
    public ResponseEntity<List<ArticleDto>> getArticle( @RequestParam int subcategory) {
        List<ArticleDto> articles = studyService.findAllById(subcategory);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("detail/article/{id}")
    public ResponseEntity<Article> getdetail(@PathVariable int id) {
        Article articles = studyService.findById(id);
        return ResponseEntity.ok(articles);
    }
}
