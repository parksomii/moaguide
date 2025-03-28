package com.moaguide.refactor.news.controller;


import com.moaguide.refactor.news.dto.NewsCustomDto;
import com.moaguide.refactor.news.service.NewsService;
import com.moaguide.refactor.security.jwt.JWTUtil;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class NewsRestController {

	private final NewsService newsService;
	private final JWTUtil jwtUtil;

	// 뉴스 홈페이지
	@GetMapping("/content/news/")
	public ResponseEntity<List<NewsCustomDto>> newsHome() {
		List<NewsCustomDto> newsData = newsService.findNews();
		return ResponseEntity.ok().body(newsData);
	}

	// 카테고리별 뉴스 조회
	@GetMapping("/content/news/{category}")
	public ResponseEntity<?> newsListByCategory(@PathVariable String category,
		@RequestParam String sort,
		@RequestParam int page,
		@RequestParam int size) {
		Page<NewsCustomDto> newsList;
		// 인기순
		if (sort.equals("popular")) {
			newsList = newsService.getAllByViews(page, size, category);
		}
		// 최신순
		else {
			newsList = newsService.getAllByLatest(page, size, category);
		}
		return ResponseEntity.ok().body(newsList);
	}

	@GetMapping("/news")
	public ResponseEntity<List<NewsCustomDto>> NewsHome() {
		List<NewsCustomDto> newsData = newsService.findNews();
		return ResponseEntity.ok().body(newsData);
	}


	@GetMapping("/news/{category}")
	public ResponseEntity<?> NewsListByCategorys(@PathVariable String category,
		@RequestParam String sort,
		@RequestParam int page,
		@RequestParam int size) {
		Page<NewsCustomDto> newsList;
		// 전체
		if (category.equals("all")) {
			newsList = newsService.getAll(page, size, sort);
		}
		// 카테고리별
		else {
			newsList = newsService.getByCategory(page, size, sort, category);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("news", newsList.getContent());
		map.put("total", newsList.getTotalPages());
		map.put("page", page);
		map.put("size", size);
		return ResponseEntity.ok().body(map);
	}
}
