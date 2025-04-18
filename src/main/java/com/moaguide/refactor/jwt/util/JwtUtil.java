package com.moaguide.refactor.jwt.util;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private final SecretKey secretKey;

	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getNickname(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("nickname", String.class);
	}

	public String getRole(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("role", String.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.getExpiration().before(new Date());
	}

	public String getCategory(String accessToken) {
		return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(accessToken).getBody()
			.get("category", String.class);
	}

	public String createJwt(String category, String nickname, String role, Long expiredMs) {

		return Jwts.builder()
			.claim("category", category)
			.claim("nickname", nickname)
			.claim("role", role)
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}


}