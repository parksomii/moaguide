package com.moaguide.controller;


import com.moaguide.domain.study.Roadmap;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.RoadmapResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
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
    public ResponseEntity<?> getroadmap(@RequestParam int page , @RequestParam int size) {
        List<Roadmap> roadmaps = studyService.findAll();
        HashMap<String,Object> response = new HashMap<>();
        response.put("roadmap",roadmaps);
        return ResponseEntity.ok(response);
    }

    @GetMapping("article")
    public ResponseEntity<ArticlelistResponseDto> getarticle(@RequestParam(defaultValue = "999999") int nextCursor) {
        ArticlelistResponseDto articleDtos = studyService.findAllByarticle(nextCursor);
        return ResponseEntity.ok(articleDtos);
    }

}
