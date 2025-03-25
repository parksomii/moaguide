package com.moaguide.refactor.article.controller;

import com.moaguide.refactor.article.dto.ArticleQueryDto;
import com.moaguide.refactor.article.service.ArticleLikeService;
import com.moaguide.refactor.article.service.ArticleQueryService;
import com.moaguide.refactor.product.entity.CategoryContent.Category;
import com.moaguide.refactor.security.jwt.JWTUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/contents")
@Slf4j
public class ArticleQueryController {

	private final ArticleQueryService articleQueryService;
	private final ArticleLikeService articleLikeService;
	private final JWTUtil jwtUtil;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getContentsByCategory(
		@RequestParam int categoryId,
		@RequestParam int page,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		Page<ArticleQueryDto> contents = articleQueryService.getContentsByCategory(categoryId,
			page);

		// JWT로부터 nickname 가져오기
		String nickname = extractNicknameFromJwt(jwt);

		// 좋아요 상태 추가
		List<Map<String, Object>> contentWithLikes = addLikeStatus(contents.getContent(), nickname);

		// 응답 데이터 생성
		Map<String, Object> response = new HashMap<>();
		response.put("content", contentWithLikes);       // 좋아요 상태가 추가된 데이터
		response.put("page", page);                     // 현재 페이지 번호
		response.put("size", contents.getSize());       // 한 페이지당 데이터 개수
		response.put("total", contents.getTotalElements()); // 전체 데이터 개수
		response.put("category", categoryId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getContentsByType(
		@RequestParam String type,
		@RequestParam String category,
		@RequestParam int page,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		Page<ArticleQueryDto> contents;

		// Category Enum으로 변환
		Category categoryEnum;
		try {
			categoryEnum = Category.fromName(category);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest()
				.body(Map.of("error", "Invalid category: " + category));
		}

		// type에 따라 서비스 호출
		if (type.equals("all")) {
			contents = articleQueryService.getContentsByAll(categoryEnum, page);
		} else if (type.equals("article")) {
			contents = articleQueryService.getContentsByType("article", categoryEnum, page);
		} else if (type.equals("video")) {
			contents = articleQueryService.getContentsByType("영상", categoryEnum, page);
		} else {
			return ResponseEntity.badRequest().body(Map.of("error", "Invalid type: " + type));
		}

		// JWT로부터 nickname 가져오기
		String nickname = extractNicknameFromJwt(jwt);

		// 좋아요 상태 추가
		List<Map<String, Object>> contentWithLikes = addLikeStatus(contents.getContent(), nickname);

		// 응답 데이터 생성
		Map<String, Object> response = new HashMap<>();
		response.put("content", contentWithLikes);       // 좋아요 상태가 추가된 데이터
		response.put("page", page);                     // 현재 페이지 번호
		response.put("size", contents.getSize());       // 한 페이지당 데이터 개수
		response.put("total", contents.getTotalElements()); // 전체 데이터 개수
		return ResponseEntity.ok(response);
	}

	/**
	 * JWT에서 nickname 추출
	 */
	private String extractNicknameFromJwt(String jwt) {
		if (jwt != null && jwt.startsWith("Bearer ")) {
			String token = jwt.substring(7); // "Bearer " 제거
			if (!jwtUtil.isExpired(token)) {
				return jwtUtil.getNickname(token);
			}
		}
		return null;
	}

	/**
	 * 아티클에 대해 좋아요 여부를 설정
	 */
	private List<Map<String, Object>> addLikeStatus(List<ArticleQueryDto> contents,
		String nickname) {
		List<Map<String, Object>> responseList = new ArrayList<>();

		for (ArticleQueryDto article : contents) {
			Map<String, Object> articleResponse = new HashMap<>();
			articleResponse.put("article", article); // ArticleQueryDto를 넣음
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
