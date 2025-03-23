package com.moaguide.service;


import com.moaguide.domain.study.ArticleRepository;
import com.moaguide.domain.study.Roadmap;
import com.moaguide.domain.study.RoadmapRepository;
import com.moaguide.dto.NewDto.ArticlelistResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyService {
    private final RoadmapRepository roadmapRepository;
    private final ArticleRepository articleRepository;

    public List<Roadmap> findAll() {
        return roadmapRepository.findAll();
    }

    public ArticlelistResponseDto findAllByarticle(int nextCursor) {
        return new ArticlelistResponseDto(articleRepository.findArticle(nextCursor, PageRequest.of(0, 20)));
    }

    public List<ArticleSummaryDto> getSummary(Pageable pageable) {
            return articleRepository.findSummaryAll(pageable);
    }

}
