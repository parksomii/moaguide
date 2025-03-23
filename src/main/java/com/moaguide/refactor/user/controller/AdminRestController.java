package com.moaguide.refactor.user.controller;

import com.moaguide.dto.UserDto;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.refactor.user.entity.User;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/moaguide/admin")
public class AdminRestController {

	private final UserService userService;
	private final JWTUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity adminlogin(@RequestBody UserDto user, HttpServletResponse response,
		HttpServletRequest request) {
		Boolean result = userService.checkPasswordByAdmin(user.getEmail(), user.getPassword());
		if (result) {
			User userdata = userService.findByEmail(user.getEmail());
			if (userdata == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 실패");
			}
			String newAccess = jwtUtil.createJwt("access", userdata.getNickname(),
				userdata.getRole().toString(), 24 * 60 * 60 * 1000L);
			response.setHeader("Authorization", "Bearer " + newAccess);
			return ResponseEntity.ok().body("로그인 성공");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 실패");
		}
	}
}
