package com.moaguide.domain.study;

import com.moaguide.dto.NewDto.customDto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink) FROM article a where a.subRoadmapId.id is null order by a.date")
    Page<ArticleDto> findArticle(Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink) FROM article a where a.subRoadmapId.id =:id order by a.date")
    List<ArticleDto> findBycategory(@Param("id") int subRoadmapId);
}
