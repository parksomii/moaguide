package com.moaguide.controller.ArticleContent;

import com.moaguide.domain.CategoryContent.Category;
import com.moaguide.dto.ArticleOverviewDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.ArticleContent.ArticleLikeService;
import com.moaguide.service.ArticleContent.ArticleOverviewService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/contents")
@AllArgsConstructor
@RestController
@Slf4j
public class ArticleOverviewController {

	private final ArticleOverviewService articleOverviewService;
	private final ArticleLikeService articleLikeService;
	private final JWTUtil jwtUtil;

	@GetMapping("/overview")
	public ResponseEntity<Map<String, Object>> getContentsOverview(
		@RequestParam(defaultValue = "4") int popularLimit,
		@RequestParam(defaultValue = "4") int recentLimit,
		@RequestParam(defaultValue = "5") int newsLimit,
		@RequestHeader(value = "Authorization", required = false) String jwt) {

		// 최신 콘텐츠 가져오기
		List<ArticleOverviewDto> recentContents = articleOverviewService.getRecentContents(
			recentLimit);
		// 인기 콘텐츠 가져오기
		List<ArticleOverviewDto> popularContents = articleOverviewService.getPopularContents(
			popularLimit);
		// 최신 뉴스 클리핑 가져오기 (카테고리 = NEWS)
		List<ArticleOverviewDto> latestNews = articleOverviewService.getNewsContents(Category.NEWS,
			newsLimit);

		// JWT를 통해 좋아요 여부 설정
		String nickname = null;
		if (jwt != null && jwt.startsWith("Bearer ")) {
			String token = jwt.substring(7); // "Bearer " 제거
			if (!jwtUtil.isExpired(token)) {
				nickname = jwtUtil.getNickname(token);
			}
		}

		// 결과를 처리할 리스트
		List<Map<String, Object>> recentResponseList = addLikeStatus(recentContents,
			nickname);
		List<Map<String, Object>> popularResponseList = addLikeStatus(popularContents,
			nickname);
		List<Map<String, Object>> newsResponseList = addLikeStatus(latestNews, nickname);

		// 응답 데이터 구성
		Map<String, Object> response = new HashMap<>();
		response.put("recentContents", recentResponseList);
		response.put("popularContents", popularResponseList);
		response.put("newsContents", newsResponseList);

		return ResponseEntity.ok(response);
	}

	/**
	 * 아티클에 대해 좋아요 여부를 설정
	 */
	private List<Map<String, Object>> addLikeStatus(List<ArticleOverviewDto> contents,
		String nickname) {
		List<Map<String, Object>> responseList = new ArrayList<>();

		for (ArticleOverviewDto article : contents) {
			Map<String, Object> articleResponse = new HashMap<>();
			articleResponse.put("article", article); // ArticleOverviewDto를 넣음
			if (nickname != null) {
				boolean liked = articleLikeService.isLikedByUser(article.getArticleId(), nickname);
				articleResponse.put("likedByMe", liked);
			} else {
				articleResponse.put("likedByMe", false); // 기본값을 false로 설정
			}
			responseList.add(articleResponse);
		}

		return responseList;
	}
}