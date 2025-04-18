package com.moaguide.refactor.login.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.dto.ProfileDto;
import com.moaguide.refactor.jwt.util.JwtUtil;
import com.moaguide.refactor.login.service.CookieService;
import com.moaguide.refactor.login.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("local")
@Slf4j
public class LocalLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final CookieService cookieService;

	public LocalLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
		CookieService cookieService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.cookieService = cookieService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) {

		//클라이언트 요청에서 username, password 추출
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		//스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			email, password, null);

		//token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain, Authentication authentication) {

		//유저 정보
		String username = authentication.getName();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		// rememberMe 여부 확인
		boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		ProfileDto user;
		if (role.equals("VIP")) {
			user = new ProfileDto(username, userDetails.getemail(), userDetails.getLoginType(),
				userDetails.getMarketing(), true);
		} else {
			user = new ProfileDto(username, userDetails.getemail(), userDetails.getLoginType(),
				userDetails.getMarketing(), false);
		}

		// 토큰 생성 - rememberMe에 따라 리프레시 토큰의 만료 시간 설정
		String accessToken = jwtUtil.createJwt("access", userDetails.getNickname(), role,
			3 * 24 * 60 * 60 * 1000L);//1시간
		long refreshTokenValidity =
			rememberMe ? 6 * 30 * 24 * 60 * 60 * 1000L : 24 * 60 * 60 * 1000L; // 6달 또는 5시간
		String refreshToken = jwtUtil.createJwt("refresh", userDetails.getNickname(), role,
			refreshTokenValidity);

		//응답 설정
		response.setHeader("Authorization", "Bearer " + accessToken);
		Cookie refreshCookie = cookieService.createLocalCookie("refresh", refreshToken,
			refreshTokenValidity);
		response.addCookie(refreshCookie);
		response.addCookie(
			cookieService.createLocalRememberMeCookie(rememberMe, refreshTokenValidity));
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			// ObjectMapper를 사용하여 ProfileDto 객체를 JSON으로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			String userJson = objectMapper.writeValueAsString(user);

			// JSON 응답 작성
			PrintWriter writer = response.getWriter();
			writer.write("{\"message\": \"Login successful\", \"user\": " + userJson + "}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException failed) {
		response.setStatus(401);
	}


}
