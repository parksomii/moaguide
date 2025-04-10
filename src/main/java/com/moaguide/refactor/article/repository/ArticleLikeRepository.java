package com.moaguide.refactor.article.repository;

import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.article.entity.ArticleContent;
import com.moaguide.refactor.article.entity.ArticleLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

	Optional<ArticleLike> findByArticleAndUser(ArticleContent article, User user);

	// 게시글 좋아요 수 조회
	@Query("SELECT COUNT(al) FROM ArticleLike al WHERE al.article.articleId = :articleId")
	int countLikesByArticleId(@Param("articleId") Long articleId);

}