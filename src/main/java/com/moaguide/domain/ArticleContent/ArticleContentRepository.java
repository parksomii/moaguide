package com.moaguide.domain.ArticleContent;

import com.moaguide.dto.NewDto.ArticleContentDto.ArticleOverviewDto;
import com.moaguide.dto.NewDto.ArticleContentDto.RelatedContentDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent, Long> {

	// 카테고리별 데이터 가져오기
	@Query("SELECT c FROM ArticleContent c WHERE c.categoryId.categoryId = :categoryId AND c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') ORDER BY c.createdAt DESC")
	Page<ArticleContent> findByCategoryId(@Param("categoryId") int categoryId, Pageable pageable);

	// 전체 데이터 가져오기
	@Query("SELECT c FROM ArticleContent c WHERE c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') ORDER BY c.createdAt DESC")
	Page<ArticleContent> findAllContent(Pageable pageable);

	// 타입과 카테고리별 데이터 가져오기
	@Query("SELECT c FROM ArticleContent c WHERE c.type = :type AND c.categoryId.categoryId = :categoryId AND c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') ORDER BY c.createdAt DESC")
	Page<ArticleContent> findByTypeAndCategoryId(@Param("type") String type,
		@Param("categoryId") int categoryId, Pageable pageable);

	// 타입별 데이터 가져오기
	@Query("SELECT c FROM ArticleContent c WHERE c.type = :type AND c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') ORDER BY c.createdAt DESC")
	Page<ArticleContent> findByTypeContent(@Param("type") String type, Pageable pageable);

	// 최신 기준 데이터 가져오기
	@Query(
		"SELECT new com.moaguide.dto.NewDto.ArticleContentDto.ArticleOverviewDto(c.articleId, c.title, c.type, c.isPremium, "
			+
			"CASE WHEN LENGTH(c.paywallUp) > 150 THEN CONCAT(SUBSTRING(c.paywallUp, 1, 150), '...') ELSE c.paywallUp END, "
			+
			"c.imgLink, cat.name) " +
			"FROM ArticleContent c JOIN c.categoryId cat " +
			"WHERE c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') " +
			"ORDER BY c.createdAt DESC"
	)
	Page<ArticleOverviewDto> findContentsWithCategory(Pageable pageable);

	// 인기 기준 데이터 가져오기
	@Query(
		"SELECT new com.moaguide.dto.NewDto.ArticleContentDto.ArticleOverviewDto(c.articleId, c.title, c.type, c.isPremium, "
			+
			"CASE WHEN LENGTH(c.paywallUp) > 150 THEN CONCAT(SUBSTRING(c.paywallUp, 1, 150), '...') ELSE c.paywallUp END, "
			+
			"c.imgLink, cat.name) " +
			"FROM ArticleContent c JOIN c.categoryId cat " +
			"WHERE c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') " +
			"ORDER BY c.views DESC"
	)
	Page<ArticleOverviewDto> findContentsByViews(Pageable pageable);

	// 카테고리 필터 추가된 최신 데이터 가져오기
	@Query(
		"SELECT new com.moaguide.dto.NewDto.ArticleContentDto.ArticleOverviewDto(c.articleId, c.title, c.type, c.isPremium, "
			+
			"CASE WHEN LENGTH(c.paywallUp) > 150 THEN CONCAT(SUBSTRING(c.paywallUp, 1, 150), '...') ELSE c.paywallUp END, "
			+
			"c.imgLink, cat.name) " +
			"FROM ArticleContent c JOIN c.categoryId cat " +
			"WHERE c.categoryId.categoryId = :categoryId AND c.createdAt <= CONVERT_TZ(NOW(), '+00:00', '+09:00') " +
			"ORDER BY c.createdAt DESC"
	)
	Page<ArticleOverviewDto> findByCategory(@Param("categoryId") int categoryId, Pageable pageable);

	// 랜덤으로 3개의 관련 아티클 가져오기
	@Query(
		"SELECT new com.moaguide.dto.NewDto.ArticleContentDto.RelatedContentDto(c.articleId, c.title, c.imgLink, c.createdAt, c.views, "
			+
			"(SELECT CAST(COUNT(al) AS long) FROM ArticleLike al WHERE al.article.articleId = c.articleId)) "
			+
			"FROM ArticleContent c " +
			"WHERE c.categoryId.categoryId = :categoryId AND c.articleId != :articleId " +
			"ORDER BY FUNCTION('RAND')")
	List<RelatedContentDto> findRelatedArticles(@Param("categoryId") Long categoryId,
		@Param("articleId") Long articleId);

	// 조회수 증가
	@Modifying
	@Query("UPDATE ArticleContent a SET a.views = a.views + 1 WHERE a.articleId = :articleId")
	void incrementViewCount(@Param("articleId") Long articleId);
}