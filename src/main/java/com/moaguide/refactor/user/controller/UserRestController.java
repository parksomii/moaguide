package com.moaguide.refactor.user.controller;

import com.moaguide.dto.NewDto.customDto.mailDto;
import com.moaguide.dto.UserDto;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.product.service.BookmarkService;
import com.moaguide.refactor.security.service.CookieService;
import com.moaguide.service.EmailService;
import com.moaguide.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserRestController {

	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final CookieService cookieService;
	private final EmailService emailService;
	private final BookmarkService bookmarkService;

	// 닉네임 수정
	@PatchMapping("/update/nickname")
	public ResponseEntity<?> updateNickname(@RequestHeader("Authorization") String auth,
		@RequestBody UserDto userDto, HttpServletResponse response) {
		String findNickname = jwtUtil.getNickname(auth.substring(7));
		String changeNickname = userDto.getNickname();
		User changeuser = userService.updateNickname(findNickname, changeNickname);
		if (changeuser == null) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
		//make new JWT
		String newAccess = jwtUtil.createJwt("access", changeuser.getNickname(),
			changeuser.getRole().name(), 30 * 60 * 1000L); // 30분
		long refreshTokenValidity = 6 * 30 * 24 * 60 * 60 * 1000L; // 6달
		String refreshToken = jwtUtil.createJwt("refresh", changeuser.getNickname(),
			changeuser.getRole().name(), refreshTokenValidity);

		Cookie expiredRefreshCookie = new Cookie("refresh", null);
		expiredRefreshCookie.setMaxAge(0); // 즉시 만료
		expiredRefreshCookie.setPath("/"); // 기존의 경로와 동일하게 설정
		expiredRefreshCookie.setHttpOnly(true);
		expiredRefreshCookie.setSecure(true); // 기존과 동일한 보안 설정
		response.addCookie(expiredRefreshCookie);

		//response
		response.setHeader("Authorization", "Bearer " + newAccess);
		Cookie refreshCookie = cookieService.createCookie("refresh", refreshToken,
			refreshTokenValidity);
		refreshCookie.setPath("/");
		response.addCookie(refreshCookie);
		return new ResponseEntity<>(changeuser.getNickname(), HttpStatus.OK);
	}


	// 비밀번호 인증
	@PostMapping("/check/password")
	public ResponseEntity<?> checkPassword(@RequestHeader("Authorization") String auth,
		@RequestBody UserDto userDto) {
		String token = auth.substring(7);
		if (!jwtUtil.isExpired(token)) {
			String nickname = jwtUtil.getNickname(token);
			String password = userDto.getPassword();
			boolean check = userService.checkPassword(nickname, password);
			if (check) {
				String newToken = jwtUtil.createJwt("verify", nickname, "pass", 1000 * 60 * 30L);
				return ResponseEntity.ok().header("verify", newToken).body("인증에 성공했습니다.");
			} else {
				return ResponseEntity.badRequest().body("fail");
			}
		} else {
			return ResponseEntity.badRequest().body("토큰이 만료되었습니다.");
		}
	}

	// 비밀번호 변경
	@PatchMapping("/update/password")
	public ResponseEntity<?> updatePassword(HttpServletRequest request,
		@RequestBody UserDto userDto) {
		String verifyToken = request.getHeader("verify");
		// JWT 토큰 검증
		if (jwtUtil.isExpired(verifyToken) && !jwtUtil.getRole(verifyToken).equals("pass")) {
			return new ResponseEntity<>("앞선 인증을 완료해주세요", HttpStatus.BAD_REQUEST);
		} else {
			userService.updatePassword(jwtUtil.getNickname(verifyToken), userDto.getPassword());
			return ResponseEntity.ok("success");
		}
	}

	@PostMapping("/send/mail")
	public ResponseEntity<?> sendMail(@RequestParam String email) {
		String code = emailService.generateVerificationCode();
		String response = emailService.sendmail(email, code);
		emailService.saveCodeToRedis(email, code);
		// 예외 처리에 따라 적절한 HTTP 상태 코드 반환
		if (response.equals("이메일 전송 완료")) {
			return ResponseEntity.ok(response);  // 200 OK
		} else if (response.equals("이메일 메시지를 생성하는 중 오류가 발생했습니다.")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("오류 발생");  // 500 Internal Server Error
		} else if (response.equals("HTML 템플릿 파일을 읽는 중 오류가 발생했습니다.")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("오류 발생");  // 500 Internal Server Error
		} else if (response.equals("이메일을 전송하는 중 오류가 발생했습니다.")) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("오류 발생");  // 503 Service Unavailable
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("오류 발생");  // 500 Internal Server Error for unexpected errors
		}
	}

	@PostMapping("/verify/mail")
	public ResponseEntity<?> verifyMail(@RequestBody mailDto mailDto) {
		boolean success = emailService.verifyCode(mailDto.getEmail(), mailDto.getCode());
		if (success) {
			String token = jwtUtil.createJwt("verify", mailDto.getEmail(), "pass", 1000 * 60 * 30L);
			return ResponseEntity.ok().header("verify", token).body("인증에 성공했습니다.");
		} else {
			return ResponseEntity.badRequest().body("인증에 실패했습니다.");
		}
	}

	@DeleteMapping("/Withdrawal")
	public ResponseEntity<?> withdrawa(HttpServletRequest request, HttpServletResponse response) {
		Cookie refreshTokenCookie = new Cookie("refresh", null);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true); // HTTPS 사용 시
		refreshTokenCookie.setPath("/"); // 모든 경로에서 쿠키를 삭제
		refreshTokenCookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
		response.addCookie(refreshTokenCookie);

		// rememberMe 쿠키 만료
		Cookie rememberMeCookie = new Cookie("rememberMe", null);
		rememberMeCookie.setHttpOnly(true);
		rememberMeCookie.setSecure(true); // HTTPS 사용 시
		rememberMeCookie.setPath("/"); // 모든 경로에서 쿠키를 삭제
		rememberMeCookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
		response.addCookie(rememberMeCookie);
		String auth = request.getHeader("Authorization");
		String nickname = jwtUtil.getNickname(auth.substring(7));
		String result = userService.delete(nickname);
		return ResponseEntity.ok().body(result);
	}

	// 관심종목 갯수 조회
	@GetMapping("/bookmark")
	public ResponseEntity<?> getBookmarkCount(
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
		int count = bookmarkService.countByUser(Nickname);
		return ResponseEntity.ok().body(count);
	}

	@PatchMapping("/update/notify")
	public ResponseEntity<?> getNotify(@RequestParam int status,
		@RequestHeader(value = "Authorization") String jwt) {
		jwt = jwt.substring(7);
		if (jwtUtil.isExpired(jwt) || jwtUtil.getNickname(jwt).equals("null")
			|| !jwtUtil.getCategory(jwt).equals("access")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			if (status > 7) {
				return ResponseEntity.badRequest().body("badRequest");
			}
			String result = userService.updateMarketing(jwtUtil.getNickname(jwt), status);
			if (result.equals("success")) {
				return ResponseEntity.ok().body(result);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}