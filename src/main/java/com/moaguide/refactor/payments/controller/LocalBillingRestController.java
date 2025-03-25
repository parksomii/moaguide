package com.moaguide.refactor.payments.controller;


import com.moaguide.refactor.payments.dto.LocalSubscriptDateDto;
import com.moaguide.refactor.payments.dto.lastLogDto;
import com.moaguide.refactor.payments.entity.PaymentLog;
import com.moaguide.refactor.payments.service.LocalBillingService;
import com.moaguide.refactor.security.jwt.JWTUtil;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
@Profile("local")
public class LocalBillingRestController {

	private final JWTUtil jwtUtil;
	private final LocalBillingService LocalbillingService;
	private final LocalBillingService localBillingService;
	@Value("${toss.secretkey}")
	private String secretkey;

	public LocalBillingRestController(JWTUtil jwtUtil, LocalBillingService LocalbillingService,
		LocalBillingService localBillingService) {
		this.jwtUtil = jwtUtil;
		this.LocalbillingService = LocalbillingService;
		this.localBillingService = localBillingService;
	}

	@PostMapping("/start")
	public ResponseEntity<?> Billingstart(
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
			LocalSubscriptDateDto date = LocalbillingService.findDate(nickname);
			if (date.getPaymentDate() != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 구독중입니다.");
			}
			Long couponId = LocalbillingService.findCoupon(nickname);
			LocalDateTime endDay = date.getEndDate() != null ? date.getEndDate() : null;
			String orderId = UUID.randomUUID().toString();
			Map<String, String> map = new HashMap<>();
			if (endDay == null) {
				if (couponId != null) {
					LocalbillingService.startWithCoupon(nickname, couponId, orderId);
				} else {
					LocalbillingService.start(nickname,
						Base64.getEncoder().encodeToString((secretkey + ":").getBytes()), orderId);
				}
				map.put("orderId", orderId);
			} else {
				LocalbillingService.startWithDate(nickname, endDay);
				String order = LocalbillingService.findLog(nickname);
				map.put("orderId", order);
			}
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카드키가 없습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> Billinglist(
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
			List<PaymentLog> paymentLog = LocalbillingService.findPayment(nickname);
			Map<String, Object> map = new HashMap<>();
			map.put("log", paymentLog);
			return ResponseEntity.ok(map);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("check/first")
	public ResponseEntity<?> firstBillingcheck(
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
			Boolean result = localBillingService.findfirstCoupon(nickname);
			Map<String, Object> status = new HashMap<>();
			status.put("status", result);
			return ResponseEntity.ok(status);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/status")
	public ResponseEntity<?> BillingStatus(
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
			LocalSubscriptDateDto date = LocalbillingService.findDate(nickname);
			lastLogDto lastLogDto = LocalbillingService.findLastLog(nickname);
			Map<String, Object> map = new HashMap<>();
			if (date == null || date.getStartDate() == null || date.getEndDate() == null) {
				map.put("status", "nonsubscribed");
				map.put("date", null);
				map.put("lastLogName", null);
				map.put("lastAmount", null);
				return ResponseEntity.ok(map);
			} else {
				map.put("lastLogName", lastLogDto.getLogname());
				map.put("lastAmount", lastLogDto.getAmount());
				if (date.getPaymentDate() != null) {
					map.put("status", "subscribed");
					map.put("date", date);
					return ResponseEntity.ok(map);
				} else {
					map.put("status", "unsubscribing");
					map.put("date", date);
					return ResponseEntity.ok(map);
				}
			}
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/stop")
	public ResponseEntity<?> Billingstop(
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
			LocalbillingService.stop(nickname);
			return ResponseEntity.ok().body("Success");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/develop/delete")
	public ResponseEntity<?> BillingdevelopDelete(
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
			LocalbillingService.developstop(nickname);
			return ResponseEntity.ok().body("Success");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
