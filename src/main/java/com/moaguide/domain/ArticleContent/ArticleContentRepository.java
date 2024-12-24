package com.moaguide.domain.ArticleContent;

import com.moaguide.dto.ArticleOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent, Long> {

    // 카테고리별 데이터 가져오기
    @Query("SELECT c FROM ArticleContent c WHERE c.categoryId.categoryId = :categoryId AND c.createdAt <= CURRENT_TIMESTAMP ORDER BY c.createdAt DESC")
    Page<ArticleContent> findByCategoryId(@Param("categoryId") int categoryId, Pageable pageable);

    // 최신 기준 데이터 가져오기
    @Query("SELECT new com.moaguide.dto.ArticleOverviewDto(c.articleId, c.title, c.type, c.isPremium, c.paywallUp, c.imgLink, cat.name) " +
            "FROM ArticleContent c JOIN c.categoryId cat " +
            "WHERE c.createdAt <= CURRENT_TIMESTAMP " +
            "ORDER BY c.createdAt DESC")
    Page<ArticleOverviewDto> findContentsWithCategory(Pageable pageable);

    // 카테고리 필터 추가된 최신 데이터 가져오기
    @Query("SELECT new com.moaguide.dto.ArticleOverviewDto(c.articleId, c.title, c.type, c.isPremium, c.paywallUp, c.imgLink, cat.name) " +
            "FROM ArticleContent c JOIN c.categoryId cat " +
            "WHERE c.categoryId.categoryId = :categoryId AND c.createdAt <= CURRENT_TIMESTAMP " +
            "ORDER BY c.createdAt DESC")
    Page<ArticleOverviewDto> findByCategory(@Param("categoryId") int categoryId, Pageable pageable);

    // 타입과 카테고리별 데이터 가져오기
    @Query("SELECT c FROM ArticleContent c WHERE c.type = :type AND c.categoryId.categoryId = :categoryId AND c.createdAt <= CURRENT_TIMESTAMP ORDER BY c.createdAt DESC")
    Page<ArticleContent> findByTypeAndCategoryId(@Param("type") String type, @Param("categoryId") int categoryId, Pageable pageable);

    // 타입별 데이터 가져오기
    @Query("SELECT c FROM ArticleContent c WHERE c.type = :type AND c.createdAt <= CURRENT_TIMESTAMP ORDER BY c.createdAt DESC")
    Page<ArticleContent> findAllByType(@Param("type") String type, Pageable pageable);
}
