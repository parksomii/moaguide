package com.moaguide.domain.study;

import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink,a.date,a.link) FROM article a where a.RoadmapId.id is null and a.id<:nextCursor order by a.id desc")
    Page<ArticleDto> findArticle(@Param("nextCursor")int nextCursor, Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleSummaryDto(a.title,a.description,a.imageLink,a.date,a.link) FROM article a where a.category =:category order by a.id Desc")
    List<ArticleSummaryDto> findSummary(@Param("category")String category, Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleSummaryDto(a.title,a.description,a.imageLink,a.date,a.link) FROM article a where a.category is not null order by a.id Desc")
    List<ArticleSummaryDto> findSummaryAll(Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleSummaryDto(a.title,a.description,a.imageLink,a.date,a.link)FROM article a where a.RoadmapId.id = :id  order by a.id Desc")
    List<ArticleSummaryDto> findByRoadmap(@Param("id")int roadmapId);
}
