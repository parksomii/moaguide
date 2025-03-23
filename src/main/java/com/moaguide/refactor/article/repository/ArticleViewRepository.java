package com.moaguide.refactor.article.repository;

import com.moaguide.refactor.article.entity.ArticleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleViewRepository extends JpaRepository<ArticleView, Long> {

}
