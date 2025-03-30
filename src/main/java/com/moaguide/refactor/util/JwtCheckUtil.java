package com.moaguide.refactor.util;

import com.moaguide.refactor.jwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtCheckUtil {

	private final JwtUtil jwtUtil;

	public String extractNickname(String token) {
		if (token == null || !token.startsWith("Bearer ")) {
			return "null";
		}

		String accessToken = token.substring(7);

		// 만료되었을 경우 null로 처리
		if (jwtUtil.isExpired(accessToken)) {
			return null;
		}

		return jwtUtil.getNickname(accessToken);
	}
}
