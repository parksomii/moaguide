package com.moaguide.refactor.article.controller;

import com.moaguide.refactor.article.dto.ArticleContentWriteRequestDto;
import com.moaguide.refactor.article.service.ArticleContentWriteService;
import com.moaguide.refactor.enums.Role;
import com.moaguide.refactor.security.jwt.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleContentWriteController {

	private final ArticleContentWriteService articleContentWriteService;
	private final JWTUtil jwtUtil;

	/**
	 * 아티클 저장 API
	 *
	 * @param jwt        JWT 토큰
	 * @param requestDto 아티클 요청 DTO
	 * @return 성공 메시지
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveArticle(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@RequestBody @Valid ArticleContentWriteRequestDto requestDto) {

		Map<String, Object> response = new HashMap<>();
		try {
			// 1. JWT 유효성 검사
			if (jwt == null || !jwt.startsWith("Bearer ")) {
				response.put("status", "error");
				response.put("message", "액세스 토큰이 없습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			String token = jwt.substring(7); // "Bearer " 제거
			if (jwtUtil.isExpired(token)) {
				response.put("status", "error");
				response.put("message", "액세스 토큰이 만료되었습니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			// 2. 작성자 권한 확인
			String role = jwtUtil.getRole(token);
			if (!Role.ADMIN.toString().equals(role)) {
				response.put("status", "error");
				response.put("message", "접근 권한이 없습니다.");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}

			// 3. 아티클 저장
			articleContentWriteService.saveArticle(requestDto);

			response.put("status", "success");
			response.put("message", "아티클이 성공적으로 저장되었습니다.");
			return ResponseEntity.ok(response);

		} catch (JwtException e) {
			response.put("status", "error");
			response.put("message", "유효하지 않은 토큰입니다.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "서버에러");
			// 에러 로그 출력
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 아티클 삭제 API
	 *
	 * @param jwt JWT 토큰
	 * @param id  삭제할 아티클 ID
	 * @return 성공 또는 실패 메시지
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteArticle(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			// 1. JWT 유효성 검사
			if (jwt == null || !jwt.startsWith("Bearer ")) {
				response.put("status", "error");
				response.put("message", "액세스 토큰이 없습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			String token = jwt.substring(7); // "Bearer " 제거
			if (jwtUtil.isExpired(token)) {
				response.put("status", "error");
				response.put("message", "액세스 토큰이 만료되었습니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			// 2. 작성자 권한 확인
			String role = jwtUtil.getRole(token);
			if (!"ADMIN".equals(role)) {
				response.put("status", "error");
				response.put("message", "접근 권한이 없습니다.");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}

			// 3. 아티클 삭제
			boolean deleted = articleContentWriteService.deleteArticleById(id);
			if (!deleted) {
				response.put("status", "error");
				response.put("message", "삭제할 아티클이 존재하지 않습니다.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.put("status", "success");
			response.put("message", "아티클이 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(response);

		} catch (JwtException e) {
			response.put("status", "error");
			response.put("message", "유효하지 않은 토큰입니다.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "서버에러");
			// 에러 로그 출력
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
