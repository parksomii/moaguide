package com.moaguide.refactor.common.controller;

import com.moaguide.dto.NewDto.customDto.AnnouncementDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.NotificationDto;
import com.moaguide.dto.NewDto.customDto.ReportAndNewsDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.refactor.news.service.NewsService;
import com.moaguide.refactor.news.service.NewsViewService;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.refactor.notice.service.AnnouncementService;
import com.moaguide.refactor.notice.service.NotificationService;
import com.moaguide.refactor.product.service.ProductService;
import com.moaguide.refactor.product.service.StudyService;
import com.moaguide.service.view.ProductViewService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Slf4j
@RequestMapping("/")
public class HomeController {

	private final NewsService newsService;
	private final StudyService articleService;
	private final NotificationService notificationService;
	private final JWTUtil jwtUtil;
	private final ProductService productService;
	private final ProductViewService productViewService;
	private final NewsViewService newsViewService;
	private final AnnouncementService announcementService;


	// 주요 리포트와 최신 이슈
	@GetMapping
	public ResponseEntity<Object> home(@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		List<ArticleSummaryDto> reportList = articleService.getSummary(pageable);
		List<NewsCustomDto> mainNews = newsService.getMainNews(pageable);
		ReportAndNewsDto reportAndNewsDto = new ReportAndNewsDto(reportList, mainNews);
		return ResponseEntity.ok(reportAndNewsDto);
	}

	// 홈페이지 알림상태 조회
	@GetMapping("notification")
	public ResponseEntity<Boolean> notification(
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String Nickname;
		// 알림 상태 확인 true or false 반환
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		boolean status = notificationService.getNotification(Nickname);
		return ResponseEntity.ok(status);
	}

	// 알림창 리스트 조회
	@GetMapping("notificationList")
	public ResponseEntity<?> notificationList(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@RequestParam(defaultValue = "0") Integer nextCursor) {
		String Nickname;
		// 알림 상태 확인 true or false 반환
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		// 알림창 리스트 반환
		List<NotificationDto> notificationList = notificationService.getNotificationList(Nickname,
			nextCursor);
		// 알림이 없으면 빈 리스트 반환
		if (notificationList == null) {
			return ResponseEntity.ok().build();
		}
		Map<String, Object> map = new HashMap<>();
		if (notificationList.size() != 10) {
			map.put("nextCursor", null);
			map.put("notification", notificationList);
		} else {
			map.put("nextCursor", notificationList.get(notificationList.size() - 1).getId());
			map.put("notification", notificationList);
		}
		return ResponseEntity.ok(map);
	}

	// 알림 읽으면 삭제
	@DeleteMapping("notification/{id}")
	public ResponseEntity<String> deleteNotification(@PathVariable("id") Long id) {
		// 읽은 알림 삭제 시 성공 여부 반환
		boolean isDeleted = notificationService.deleteNotification(id);

		// 삭제 실패 시 에러 처리
		if (!isDeleted) {
			return ResponseEntity.badRequest().body("알림을 삭제하는데 실패했습니다.");
		}
		return ResponseEntity.ok("success");
	}

	// 홈페이지 상품 리스트 조회
	@GetMapping("/home/list")
	public ResponseEntity<?> homelist(@RequestParam String category,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String Nickname;
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		List<SummaryCustomDto> result = productService.home(category, Nickname);
		if (result == null) {
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
		} else {
			Map<String, Object> response = new HashMap<>();
			response.put("product", result);
			return ResponseEntity.ok(response);
		}
	}

	// 상품 조회수 증가
	@PostMapping("/product/view/{productId}")
	public ResponseEntity<String> InsertProductView(@PathVariable String productId,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String nickname = "null";

		// JWT가 존재하는지, 그리고 유효한지 확인
		if (jwt != null && jwt.startsWith("Bearer ")) {
			String token = jwt.substring(7); // "Bearer " 제거한 토큰
			if (!jwtUtil.isExpired(token)) {
				// 토큰이 유효한 경우 닉네임 추출
				nickname = jwtUtil.getNickname(token);
			} else {
				// 토큰이 만료된 경우
				return ResponseEntity.status(401).body("토큰이 만료되었습니다. 다시 로그인 해주세요.");
			}
		}

		try {
			// 닉네임과 관계없이 서비스 호출
			productViewService.insert(productId, nickname);
			productService.viewupdate(productId);
			return ResponseEntity.ok("조회수 추가 성공");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
		}
	}

	// 뉴스 조회수 추가
	@PostMapping("news/view/{NewsId}")
	public ResponseEntity<?> detail_check(@PathVariable Long NewsId,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String nickname = "null";

		// JWT가 존재하는지, 그리고 유효한지 확인
		if (jwt != null && jwt.startsWith("Bearer ")) {
			String token = jwt.substring(7); // "Bearer " 제거한 토큰
			if (!jwtUtil.isExpired(token)) {
				// 토큰이 유효한 경우 닉네임 추출
				nickname = jwtUtil.getNickname(token);
			} else {
				// 토큰이 만료된 경우
				return ResponseEntity.status(401).body("토큰이 만료되었습니다. 다시 로그인 해주세요.");
			}
		}

		try {
			// 닉네임과 관계없이 서비스 호출
			newsViewService.insert(NewsId, nickname);
			newsService.viewupdate(NewsId);
			return ResponseEntity.ok("조회수 추가 성공");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
		}
	}

	// 공지사항 조회
	@GetMapping("notice")
	public ResponseEntity<Object> noticeList(@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<AnnouncementDto> noticeList = announcementService.getNotice(pageable);
		// 공지가 없으면 빈 리스트 반환
		if (noticeList == null) {
			return ResponseEntity.ok().build();
		}
		Map<String, Object> response = new HashMap<>();
		response.put("size", size);
		response.put("page", page);
		response.put("total", (int) Math.ceil((double) noticeList.getTotalElements() / 10));
		response.put("notice", noticeList.getContent());
		return ResponseEntity.ok(response);
	}

	// 공지사항 상세 조회
	@GetMapping("notice/{id}")
	public ResponseEntity<?> noticeDetail(@PathVariable Long id) {
		AnnouncementDto notice = announcementService.getNoticeDetail(id);
		return ResponseEntity.ok(notice);
	}
}
