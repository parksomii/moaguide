package com.moaguide.domain.study;

import com.moaguide.dto.NewDto.customDto.ArticleDto;
import com.moaguide.refactor.product.dto.ArticleSummaryDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleDto(a.id,a.title,a.description,a.imageLink,a.date,a.link) FROM article a where a.category ='\uD83D\uDCBC 사회초년생 재테크 가이드' and a.id<:nextCursor order by a.id desc")
	Page<ArticleDto> findArticle(@Param("nextCursor") int nextCursor, Pageable pageable);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.ArticleSummaryDto(a.title,a.description,a.imageLink,a.date,a.link) FROM article a where not a.category = '\uD83D\uDCBC 사회초년생 재테크 가이드' order by a.id Desc")
	List<ArticleSummaryDto> findSummaryAll(Pageable pageable);
}