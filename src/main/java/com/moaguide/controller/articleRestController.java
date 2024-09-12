package com.moaguide.controller;


import com.moaguide.domain.study.Article;
import com.moaguide.domain.study.Roadmap;
import com.moaguide.domain.study.SubRoadmap;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.RoadmapResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
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

        @GetMapping("guide/sub")
    public ResponseEntity<List<SubRoadmap>> getsubroadmap(@RequestParam int category) {
        List<SubRoadmap> subRoadmap = studyService.findAllbysub(category);
        return ResponseEntity.ok(subRoadmap);
    }

    @GetMapping("guide/{subcategory}")
    public ResponseEntity<List<ArticleDto>> getArticle( @PathVariable int subcategory) {
        List<ArticleDto> articles = studyService.findAllById(subcategory);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("detail/article")
    public ResponseEntity<Article> getdetail(@RequestParam int id) {
        Article articles = studyService.findById(id);
        return ResponseEntity.ok(articles);
    }
}
