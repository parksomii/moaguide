package com.moaguide.refactor.article.service;

import com.moaguide.refactor.article.entity.ArticleContent;
import com.moaguide.refactor.article.entity.ArticleLike;
import com.moaguide.refactor.article.repository.ArticleContentRepository;
import com.moaguide.refactor.article.repository.ArticleLikeRepository;
import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

	private final ArticleLikeRepository articleLikeRepository;
	private final UserRepository userRepository;
	private final ArticleContentRepository articleContentRepository;

	@Transactional
	public boolean toggleLike(Long articleId, String nickname) {
		// 사용자 확인
		User user = userRepository.findByNickname(nickname)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		// 아티클 확인
		ArticleContent article = articleContentRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("아티클을 찾을 수 없습니다."));

		// 좋아요 여부 확인
		Optional<ArticleLike> existingLike = articleLikeRepository.findByArticleAndUser(article,
			user);

		if (existingLike.isPresent()) {
			// 이미 좋아요가 있으면 삭제
			articleLikeRepository.delete(existingLike.get());
			return false; // 좋아요 취소
		} else {
			// 좋아요가 없으면 새로 추가
			ArticleLike newLike = new ArticleLike(article, user);
			articleLikeRepository.save(newLike);
			return true; // 좋아요 추가
		}
	}

	// 좋아요 수를 반환하는 메서드 추가
	public int getLikeCount(Long articleId) {
		return articleLikeRepository.countLikesByArticleId(articleId);
	}

	// 사용자가 특정 아티클에 대해 좋아요를 눌렀는지 확인하는 메서드 추가
	public boolean isLikedByUser(Long articleId, String nickname) {
		// 사용자 확인
		User user = userRepository.findByNickname(nickname)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		// 아티클 확인
		ArticleContent article = articleContentRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("아티클을 찾을 수 없습니다."));

		// 해당 아티클에 대해 사용자가 좋아요를 눌렀는지 확인
		Optional<ArticleLike> existingLike = articleLikeRepository.findByArticleAndUser(article,
			user);
		return existingLike.isPresent(); // 좋아요를 눌렀다면 true, 아니면 false
	}
}

