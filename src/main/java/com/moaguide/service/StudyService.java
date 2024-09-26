package com.moaguide.service;


import com.moaguide.domain.study.*;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.dto.NewDto.customDto.SubRoadmapDto;
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
    private final SubRoadmapRepository subRoadmapRepository;
    private final ArticleRepository articleRepository;

    public Page<Roadmap> findAll(Pageable pageable) {
        return roadmapRepository.findAll(pageable);
    }

    public ArticlelistResponseDto findAllByarticle(int nexCursur) {
        return new ArticlelistResponseDto(articleRepository.findArticle(nexCursur, PageRequest.of(0, 10)));
    }

    public List<SubRoadmapDto> findAllbysub(int category) {
        return subRoadmapRepository.findAllDto(category);
    }

    public List<ArticleDto> findAllById(int subcategory) {
        return articleRepository.findBycategory(subcategory);
    }

    public ArticleDto findById(int id) {
        return  articleRepository.findById(id);
    }

    public List<ReportCustomDto> getSummary(String category, Pageable pageable) {
        return  articleRepository.findSummary(category,pageable);
    }
}
