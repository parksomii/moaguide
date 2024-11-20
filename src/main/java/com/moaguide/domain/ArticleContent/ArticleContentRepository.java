package com.moaguide.domain.ArticleContent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent, Integer> {

    Page<ArticleContent> findByCategoryId(int categoryId, Pageable pageable);
}
