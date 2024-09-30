package com.moaguide.service;


import com.moaguide.domain.study.*;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyService {
    private final RoadmapRepository roadmapRepository;
    private final ArticleRepository articleRepository;

    public Page<Roadmap> findAll(Pageable pageable) {
        return roadmapRepository.findAll(pageable);
    }

    public ArticlelistResponseDto findAllByarticle(int nextCursor) {
        return new ArticlelistResponseDto(articleRepository.findArticle(nextCursor, PageRequest.of(0, 10)));
    }

    public List<ArticleSummaryDto> getSummary(String category, Pageable pageable) {
        if (category.equals("all")) {
            return articleRepository.findSummaryAll(pageable);
        } else {
            return articleRepository.findSummary(category, pageable);
        }
    }

    public List<ArticleSummaryDto> findByRoadmap(int roadmapId) {
        return articleRepository.findByRoadmap(roadmapId);
    }
}
