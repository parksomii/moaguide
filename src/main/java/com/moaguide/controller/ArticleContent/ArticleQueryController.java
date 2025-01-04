package com.moaguide.controller.ArticleContent;

import com.moaguide.domain.CategoryContent.Category;
import com.moaguide.dto.ArticleQueryDto;
import com.moaguide.service.ArticleContent.ArticleQueryService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/contents")
@Slf4j
public class ArticleQueryController {

	private final ArticleQueryService articleQueryService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getContentsByCategory(
		@RequestParam int categoryId,
		@RequestParam int page) {
		Page<ArticleQueryDto> contents = articleQueryService.getContentsByCategory(categoryId,
			page);

		Map<String, Object> response = new HashMap<>();
		response.put("content", contents.getContent()); // 현재 페이지 데이터
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
		@RequestParam int page) {
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
			contents = articleQueryService.getContentsByType("아티클", categoryEnum, page);
		} else if (type.equals("video")) {
			contents = articleQueryService.getContentsByType("영상", categoryEnum, page);
		} else {
			return ResponseEntity.badRequest().body(Map.of("error", "Invalid type: " + type));
		}

		// 응답 데이터 생성
		Map<String, Object> response = new HashMap<>();
		response.put("content", contents.getContent()); // 현재 페이지 데이터
		response.put("page", page);                     // 현재 페이지 번호
		response.put("size", contents.getSize());       // 한 페이지당 데이터 개수
		response.put("total", contents.getTotalElements()); // 전체 데이터 개수
		return ResponseEntity.ok(response);
	}
}

