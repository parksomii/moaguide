package com.moaguide.domain.ArticleContent;

import com.moaguide.dto.ContentOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent, Integer> {

    // 카테고리별 데이터 가져오기
    @Query("SELECT c FROM ArticleContent c WHERE c.categoryId = :categoryId AND c.createdAt <= CURRENT_TIMESTAMP ORDER BY c.createdAt DESC")
    Page<ArticleContent> findByCategoryId(@Param("categoryId") int categoryId, Pageable pageable);

    // 최신 기준 데이터 가져오기
    @Query("SELECT new com.moaguide.dto.ContentOverviewDto(c.contentId, c.title, c.type, c.isPremium, c.description, c.img_link, cat.name) "
            +
            "FROM ArticleContent c JOIN CategoryContent cat ON c.categoryId = cat.categoryId " +
            "WHERE c.createdAt <= CURRENT_TIMESTAMP " +
            "ORDER BY c.createdAt DESC")
    Page<ContentOverviewDto> findContentsWithCategory(Pageable pageable);

    // 카테고리 필터 추가된 최신 데이터 가져오기
    @Query("SELECT new com.moaguide.dto.ContentOverviewDto(c.contentId, c.title, c.type, c.isPremium, c.description, c.img_link, cat.name) "
            +
            "FROM ArticleContent c JOIN CategoryContent cat ON c.categoryId = cat.categoryId " +
            "WHERE c.categoryId = :categoryId AND c.createdAt <= CURRENT_TIMESTAMP " +
            "ORDER BY c.createdAt DESC")
    Page<ContentOverviewDto> findByCategory(@Param("categoryId") int categoryId,
                                            Pageable pageable);
}
