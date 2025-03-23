package com.moaguide.refactor.article.controller;

import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.service.ArticleContent.ArticleLikeService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleLikeController {

	private final ArticleLikeService articleLikeService;
	private final JWTUtil jwtUtil;

	@PostMapping("/{articleId}/like")
	public ResponseEntity<Object> toggleLike(
		@RequestHeader(value = "Authorization") String jwt,
		@PathVariable Long articleId) {

		// JWT 유효성 검사
		if (jwt == null || !jwt.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
		}

		String token = jwt.substring(7); // "Bearer " 제거
		if (jwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("액세스 토큰이 만료되었습니다.");
		}

		// 사용자 Nickname 가져오기
		String nickname = jwtUtil.getNickname(token);

		// 좋아요 토글
		boolean liked = articleLikeService.toggleLike(articleId, nickname);

		// 현재 좋아요 수 반환
		int likeCount = articleLikeService.getLikeCount(articleId);

		// 응답 생성
		Map<String, Object> response = new HashMap<>();
		response.put("liked", liked);
		response.put("likeCount", likeCount);
		response.put("message", liked ? "좋아요를 눌렀습니다." : "좋아요를 취소했습니다.");

		return ResponseEntity.ok(response);
	}
}
