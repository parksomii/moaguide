package com.moaguide.refactor.payments.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.refactor.payments.service.LocalBillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/card")
@Profile("local")
public class LocalCardRestController {

	private final JWTUtil jwtUtil;
	private final LocalBillingService billingService;

	@Value("${toss.secretkey}")
	private String secretkey;

	public LocalCardRestController(JWTUtil jwtUtil, LocalBillingService billingService) {
		this.jwtUtil = jwtUtil;
		this.billingService = billingService;
	}

	@GetMapping("/mycard")
	public ResponseEntity<?> myCard(
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		try {
			if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 없습니다.");
			}
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 만료되었습니다.");
			}
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			User user = billingService.findByNickanme(nickname);
			Map<String, Object> map = new HashMap<>();
			map.put("cardName", user.getCardname());
			map.put("cardNumber", user.getCardNumber());
			return ResponseEntity.ok(map);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/create/card")
	public ResponseEntity<?> createCard(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@RequestParam String customerKey, @RequestParam String authKey) {
		try {
			if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 없습니다.");
			}
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 만료되었습니다.");
			}
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			String base64SecretKey = Base64.getEncoder()
				.encodeToString((secretkey + ":").getBytes());
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.tosspayments.com/v1/billing/authorizations/issue"))
				.header("Authorization", "Basic " + base64SecretKey)
				.header("Content-Type", "application/json")
				.method("POST", HttpRequest.BodyPublishers.ofString(
					"{\"authKey\":\"" + authKey + "\",\"customerKey\":\"" + customerKey + "\"}"))
				.build();
			HttpResponse<String> response = HttpClient.newHttpClient()
				.send(request, HttpResponse.BodyHandlers.ofString());
			int statusCode = response.statusCode();
			if (statusCode > 200) {
				// 에러 발생 시 로그와 사용자 친화적 메시지 반환
				String errorMessage = String.format(
					"Request failed with status code %d and body: %s",
					statusCode, response.body());
				System.err.println(errorMessage); // 로그로 에러 확인
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Failed to create card: " + response.body());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response.body());
			billingService.save(nickname, rootNode.get("cardCompany").asText(),
				Integer.valueOf(rootNode.get("cardNumber").asText().substring(0, 2)), customerKey,
				rootNode.get("billingKey").asText());
			Map<String, Object> map = new HashMap<>();
			map.put("cardName", rootNode.get("cardCompany").asText());
			map.put("cardNumber",
				Integer.valueOf(rootNode.get("cardNumber").asText().substring(0, 2)));
			return ResponseEntity.ok().body(map);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("카드가 이미 발급되어있습니다.");
		} catch (SQLIntegrityConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("카드가 이미 발급되어있습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/card")
	public ResponseEntity<?> deleteCard(
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		try {
			if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 없습니다.");
			}
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 만료되었습니다.");
			}
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			billingService.delete(nickname);
			return ResponseEntity.ok().body("카드를 성공적으로 삭제했습니다.");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PatchMapping("/update/card")
	public ResponseEntity<?> updateCard(
		@RequestHeader(value = "Authorization", required = false) String jwt,
		@RequestParam String customerKey, @RequestParam String authKey) {
		try {
			if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 없습니다.");
			}
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("액세스 토큰이 만료되었습니다.");
			}
			String nickname = jwtUtil.getNickname(jwt.substring(7));
			String base64SecretKey = Base64.getEncoder()
				.encodeToString((secretkey + ":").getBytes());
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.tosspayments.com/v1/billing/authorizations/issue"))
				.header("Authorization", "Basic " + base64SecretKey)
				.header("Content-Type", "application/json")
				.method("POST", HttpRequest.BodyPublishers.ofString(
					"{\"authKey\":\"" + authKey + "\",\"customerKey\":\"" + customerKey + "\"}"))
				.build();
			HttpResponse<String> response = HttpClient.newHttpClient()
				.send(request, HttpResponse.BodyHandlers.ofString());
			int statusCode = response.statusCode();
			if (statusCode > 200) {
				// 에러 발생 시 로그와 사용자 친화적 메시지 반환
				String errorMessage = String.format(
					"Request failed with status code %d and body: %s",
					statusCode, response.body());
				System.err.println(errorMessage); // 로그로 에러 확인
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Failed to create card: " + response.body());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response.body());
			billingService.update(nickname, rootNode.get("cardCompany").asText(),
				Integer.valueOf(rootNode.get("card").get("number").asText().substring(0, 2)),
				customerKey, rootNode.get("billingKey").asText());
			Map<String, Object> map = new HashMap<>();
			map.put("cardName", rootNode.get("cardCompany").asText());
			map.put("cardNumber",
				Integer.valueOf(rootNode.get("card").get("number").asText().substring(0, 2)));
			return ResponseEntity.ok().body(map);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate key");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
