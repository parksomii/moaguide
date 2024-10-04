    package com.moaguide.config.handler;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.moaguide.domain.user.User;
    import com.moaguide.dto.NewDto.oauth.CustomOAuth2User;
    import com.moaguide.jwt.JWTUtil;
    import com.moaguide.service.CookieService;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.AllArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Map;

    @AllArgsConstructor
    public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

        private final JWTUtil jwtUtil;
        private final CookieService cookieService;


        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            // Authentication 객체에서 CustomOAuth2User 정보 가져오기
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            User user = customOAuth2User.getUser();  // User 정보 가져오기

            // 공통 응답 설정
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            // ObjectMapper와 PrintWriter를 한번만 생성
            ObjectMapper objectMapper = new ObjectMapper();
            PrintWriter writer = response.getWriter();

            try {
                if (user.getNickname() == null || user.getNickname().isEmpty()) {
                    // 이름이 없는 경우, 검증용 토큰 발급 및 JSON 응답 반환
                    String token = jwtUtil.createJwt("verify", user.getPhoneNumber(), "pass", 1000 * 60 * 30L);
                    String email = user.getEmail();
                    response.setHeader("verify", token);  // 토큰을 헤더에 설정

                    response.setStatus(HttpStatus.OK.value());
                    writer.write(objectMapper.writeValueAsString(
                            Map.of(
                                    "email", email,  // JSON 응답
                                    "URL", "https://moaguide.com/signup"  // URL 추가
                            )
                    ));
                    writer.flush();
                } else {
                    // 이름이 있는 경우, AccessToken 및 RefreshToken 발급
                    String accessToken = jwtUtil.createJwt("access", user.getName(), String.valueOf(user.getRole()), 60 * 1000L);
                    String refreshToken = jwtUtil.createJwt("refresh", user.getName(), String.valueOf(user.getRole()), 5 * 60 * 60 * 1000L);

                    // 응답 설정: AccessToken은 헤더, RefreshToken은 쿠키로 설정
                    response.setHeader("Authorization", "Bearer " + accessToken);
                    cookieService.setCookieWithSameSite(response, "refresh", refreshToken, 5 * 60 * 60 * 1000L);
                    cookieService.setCookieWithSameSite(response, "rememberMe", "false", 5 * 60 * 60 * 1000L);

                    response.setStatus(HttpStatus.OK.value());
                    String userJson = objectMapper.writeValueAsString(user);
                    writer.write(objectMapper.writeValueAsString(
                            Map.of(
                                    "message", "Login successful",
                                    "user", userJson,
                                    "URL", "https://moaguide.com/signup"  // URL 추가
                            )
                    ));
                    writer.flush();
                }
            } catch (IOException e) {
                // 에러 처리: 500 응답 전송
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                writer.write("{\"message\": \"An error occurred during authentication\"}");
                writer.flush();
            }

        }
    }