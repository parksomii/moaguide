package com.moaguide.domain.study;

import com.moaguide.dto.NewDto.customDto.ArticleDto;
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

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink) FROM article a where a.subRoadmapId.id is null and a.id<:nextCursur order by a.id desc")
    Page<ArticleDto> findArticle(@Param("nextCursur")int nextCursur,Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink) FROM article a where a.subRoadmapId.id =:id order by a.id")
    List<ArticleDto> findBycategory(@Param("id") int subRoadmapId);

    @Query("SELECT  new com.moaguide.dto.NewDto.customDto.ArticleDto(a.title,a.date,a.imageLink,a.content,a.pdfLink) from article a where a.id = :id")
    ArticleDto findById(@Param("id") int id);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink) FROM article a where a.category =:category order by a.id")
    List<ReportCustomDto> findSummary(@Param("category")String category, Pageable pageable);
}
