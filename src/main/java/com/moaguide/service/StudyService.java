package com.moaguide.service;


import com.moaguide.domain.study.*;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyService {
    private final RoadmapRepository roadmapRepository;
    private final SubRoadmapRepository subRoadmapRepository;
    private final ArticleRepository articleRepository;

    public Page<Roadmap> findAll(Pageable pageable) {
        return roadmapRepository.findAll(pageable);
    }

    public ArticlelistResponseDto findAllByarticle(Pageable pageable) {
        return new ArticlelistResponseDto(articleRepository.findArticle(pageable));
    }

    public List<SubRoadmap> findAllbysub(int category) {
        return subRoadmapRepository.findAll(category);
    }

    public List<ArticleDto> findAllById(int subcategory) {
        return articleRepository.findBycategory(subcategory);
    }
}
