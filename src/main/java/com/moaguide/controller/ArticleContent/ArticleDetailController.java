package com.moaguide.controller.ArticleContent;

import com.moaguide.dto.NewDto.ArticleContentDto.RelatedContentDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.ArticleContent.ArticleDetailService;
import com.moaguide.service.ArticleContent.ArticleLikeService;
import com.moaguide.service.ArticleContent.ArticleViewService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles/detail")
@RequiredArgsConstructor
public class ArticleDetailController {

	private final ArticleDetailService articleDetailService;
	private final ArticleViewService articleViewService; // 조회 기록 서비스
	private final JWTUtil jwtUtil;
	private final ArticleLikeService articleLikeService;

	@GetMapping("/{articleId}")
	public ResponseEntity<Object> getArticleDetail(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@PathVariable Long articleId) {

		// JWT 유효성 검사
		if (jwt == null || !jwt.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
		}

		String token = jwt.substring(7); // "Bearer " 제거
		if (jwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("액세스 토큰이 만료되었습니다.");
		}

		// Role 확인
		String role = jwtUtil.getRole(token);

		// 조회수 증가 기록 저장
		articleViewService.insert(articleId, jwtUtil.getNickname(token));

		// 아티클 조회수 증가
		articleDetailService.incrementViews(articleId);

		// 서비스 호출
		Object articleDetail = articleDetailService.getArticleDetail(articleId, role);

		// 좋아요 여부 추가
		String nickname = jwtUtil.getNickname(token);
		Map<String, Object> response = new HashMap<>();
		if (articleDetail instanceof Map) {
			response.putAll((Map<String, Object>) articleDetail);
		} else {
			response.put("articleDetail", articleDetail);
		}
		boolean likedByMe =
			(nickname != null) && articleLikeService.isLikedByUser(articleId, nickname);
		response.put("likedByMe", likedByMe);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{articleId}/related")
	public ResponseEntity<Object> getRelatedArticles(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@PathVariable Long articleId) {

		// JWT 유효성 검사
		if (jwt == null || !jwt.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
		}

		String token = jwt.substring(7); // "Bearer " 제거
		if (jwtUtil.isExpired(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("액세스 토큰이 만료되었습니다.");
		}

		// nickname 확인
		String nickname = jwtUtil.getNickname(token);

		// 관련 콘텐츠 서비스 호출
		List<RelatedContentDto> relatedArticles = articleDetailService.getRelatedArticles(
			articleId);

		// 관련 콘텐츠가 없는 경우
		if (relatedArticles.isEmpty()) {
			return ResponseEntity.ok("이전 글이 없습니다.");
		}

		// 좋아요 여부 추가
		List<Map<String, Object>> responseList = addLikeStatus(relatedArticles,
			nickname);

		return ResponseEntity.ok(responseList);
	}

	/**
	 * 관련 아티클에 대해 좋아요 여부를 설정
	 */
	private List<Map<String, Object>> addLikeStatus(
		List<RelatedContentDto> relatedArticles,
		String nickname) {
		List<Map<String, Object>> responseList = new ArrayList<>();

		for (RelatedContentDto article : relatedArticles) {
			Map<String, Object> articleResponse = new HashMap<>();
			articleResponse.put("article", article); // RelatedContentDto를 넣음
			if (nickname != null) {
				// 좋아요 여부를 확인하는 서비스 호출 (이 부분은 articleLikeService 메서드를 통해 처리)
				boolean liked = articleLikeService.isLikedByUser(article.getArticleId(), nickname);
				articleResponse.put("likedByMe", liked); // 사용자가 좋아요를 눌렀는지 여부
			} else {
				articleResponse.put("likedByMe", false); // 기본값을 false로 설정
			}
			responseList.add(articleResponse);
		}

		return responseList;
	}
}