package com.moaguide.refactor.payments.controller;


import com.moaguide.refactor.jwt.util.JwtUtil;
import com.moaguide.refactor.payments.dto.SubscriptDateDto;
import com.moaguide.refactor.payments.dto.lastLogDto;
import com.moaguide.refactor.payments.entity.PaymentLog;
import com.moaguide.refactor.payments.service.BillingService;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Profile({"blue", "green"})
public class BillingRestController {

	private final JwtUtil jwtUtil;
	private final BillingService billingService;
	@Value("${toss.secretkey}")
	private String secretkey;

	public BillingRestController(JwtUtil jwtUtil, BillingService billingService) {
		this.jwtUtil = jwtUtil;
		this.billingService = billingService;
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
			SubscriptDateDto date = billingService.findDate(nickname);
			if (date.getPaymentDate() != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 구독중입니다.");
			}
			Long couponId = billingService.findCoupon(nickname);
			LocalDate endDay = date.getEndDate() != null ? date.getEndDate() : null;
			if (endDay == null) {
				if (couponId != null) {
					billingService.startWithCoupon(nickname, couponId);
				} else {
					billingService.start(nickname,
						Base64.getEncoder().encodeToString((secretkey + ":").getBytes()));
				}
			} else {
				billingService.startWithDate(nickname, endDay);
			}
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("카드키나 쿠폰id가 잘못되었습니다.");
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
			List<PaymentLog> paymentLog = billingService.findPayment(nickname);
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
			Boolean result = billingService.findfirstCoupon(nickname);
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
			SubscriptDateDto date = billingService.findDate(nickname);
			lastLogDto lastLogDto = billingService.findLastLog(nickname);
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
			billingService.stop(nickname);
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
			billingService.developstop(nickname);
			return ResponseEntity.ok().body("Success");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
