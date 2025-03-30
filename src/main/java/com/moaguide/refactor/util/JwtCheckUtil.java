package com.moaguide.refactor.util;


import com.moaguide.refactor.jwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtCheckUtil {
	private final JwtUtil jwtUtil;

	public static boolean jwtCheck(String token) {

	}
}
