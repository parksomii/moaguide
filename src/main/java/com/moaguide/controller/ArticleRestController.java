package com.moaguide.controller;


import com.moaguide.domain.study.Roadmap;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.refactor.product.service.StudyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/study/")
public class ArticleRestController {
    private final StudyService studyService;

    @GetMapping("guide")
    public ResponseEntity<?> getroadmap() {
        List<Roadmap> roadmaps = studyService.findAll();
        HashMap<String,Object> response = new HashMap<>();
        response.put("roadmap",roadmaps);
        response.put("nextCursor",null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("article")
    public ResponseEntity<ArticlelistResponseDto> getarticle(@RequestParam(defaultValue = "999999") int nextCursor) {
        ArticlelistResponseDto articleDtos = studyService.findAllByarticle(nextCursor);
        return ResponseEntity.ok(articleDtos);
    }

}
