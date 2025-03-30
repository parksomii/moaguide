package com.moaguide.refactor.product.controller;

import com.moaguide.refactor.jwt.util.JwtUtil;
import com.moaguide.refactor.product.dto.ArticleSummaryDto;
import com.moaguide.refactor.product.dto.SummaryCustomDto;
import com.moaguide.refactor.product.dto.SummaryIssupriceCustomDto;
import com.moaguide.refactor.product.dto.SummaryRecentDto;
import com.moaguide.refactor.product.dto.SummaryResponseDto;
import com.moaguide.refactor.product.service.BookmarkService;
import com.moaguide.refactor.product.service.CurrentDivideService;
import com.moaguide.refactor.product.service.ProductService;
import com.moaguide.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/summary")
public class SummaryRestController {

	private final StudyService articleService;
	private final ProductService productService;
	private final CurrentDivideService divideService;
	private final BookmarkService bookmarkService;
	private final JwtUtil jwtUtil;

	@PostMapping("/bookmark/{productId}")
	public ResponseEntity<String> bookmark(@PathVariable String productId,
		@RequestHeader(value = "Authorization", required = true) String jwt) {
		if (jwt != null && jwt.startsWith("Bearer ") && !jwtUtil.isExpired(jwt.substring(7))) {
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			try {
				bookmarkService.postBookmark(productId, nickname);
				return ResponseEntity.ok("북마크 성공");
			} catch (DataIntegrityViolationException e) {
				return ResponseEntity.badRequest().body("북마크 실패:중복");
			} catch (RuntimeException e) {
				return ResponseEntity.internalServerError().body("서버오류");
			}
		} else {
			return ResponseEntity.badRequest().body("유효하지 않은 토큰");
		}
	}

	@DeleteMapping("/bookmark/{productId}")
	public ResponseEntity<String> bookmarkDelete(@PathVariable String productId,
		HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		if (jwt != null && jwt.startsWith("Bearer ") && !jwtUtil.isExpired(jwt.substring(7))) {
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			try {
				bookmarkService.deleteBookmark(productId, nickname);
				return ResponseEntity.ok().body("북마크 해제 성공");
			} catch (RuntimeException e) {
				return ResponseEntity.badRequest().body("북마크 해제 실패: " + e.getMessage());
			}
		} else {
			return ResponseEntity.badRequest().body("유효하지 않은 토큰");
		}
	}


	@GetMapping
	public ResponseEntity<SummaryRecentDto> getSummary() {
		Pageable pageable = PageRequest.of(0, 10);
		Date date = Date.valueOf(LocalDate.now());
		List<SummaryIssupriceCustomDto> divide = productService.findrecent(pageable, date);
		List<SummaryCustomDto> customDtos = productService.getlist(0, 3, "lastDivide_rate desc",
			"null");
		List<ArticleSummaryDto> reportList = articleService.getSummary(pageable);
		return ResponseEntity.ok(new SummaryRecentDto(divide, customDtos, reportList));
	}

	// 카테고리별 상품현황 목록 조회
	@GetMapping("/list")
	public ResponseEntity<?> summary(@RequestParam String category,
		@RequestParam String subcategory,
		@RequestParam String sort,
		@RequestParam int page,
		@RequestParam int size,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		page = page - 1;
		String Nickname;
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		if (subcategory.equals("trade")) {
			List<SummaryCustomDto> summary;
			if (category.equals("all")) {
				summary = productService.getlist(page, size, sort, Nickname);
				int total = productService.getlistTotal("거래중");
				return ResponseEntity.ok().body(new SummaryResponseDto(summary, page, size, total));
			} else if (category.equals("bookmark")) {
				summary = productService.getlistBookmark(page, size, sort, Nickname);
				int total = productService.getlistTotalByBookmark("거래중", Nickname);
				return ResponseEntity.ok().body(new SummaryResponseDto(summary, page, size, total));
			} else {
				summary = productService.getcategorylist(page, size, sort, category, Nickname);
				int total = productService.getlistTotalCategory("거래중", category);
				return ResponseEntity.ok(new SummaryResponseDto(summary, page, size, total));
			}
		} else if (subcategory.equals("start")) {
			SummaryResponseDto inavete;
			if (sort.equals("ready")) {
				inavete = productService.getreadylist(page, size, category, Nickname);
				return ResponseEntity.ok(inavete);
			} else if (sort.equals("start")) {
				inavete = productService.getstartlisty(page, size, category, Nickname);
				return ResponseEntity.ok(inavete);
			}
		} else if (subcategory.equals("end")) {
			SummaryResponseDto inavete;
			if (sort.equals("end")) {
				inavete = productService.getend(page, size, category, Nickname);
				return ResponseEntity.ok(inavete);
			} else if (sort.equals("finish")) {
				inavete = productService.getfinish(page, size, category, Nickname);
				return ResponseEntity.ok(inavete);
			}
		} else if (subcategory.equals("bookmark") && sort.equals("bookmark")) {
			SummaryResponseDto invate;
			invate = bookmarkService.getProductBybookmark(category, Nickname, page, size);
			return ResponseEntity.ok(invate);
		}
		return ResponseEntity.badRequest().body("잘못된 요청입니다.");
	}

}