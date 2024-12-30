package com.moaguide.controller;


import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CookieService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Profile({"blue","green"})
public class TokenRestController {
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;

    @PostMapping("/token/refresh")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        String remember = null;
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return new ResponseEntity<>("cookies null", HttpStatus.BAD_REQUEST);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
            if (cookie.getName().equals("rememberMe")) {
                remember = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getNickname(refresh);
        String role = jwtUtil.getRole(refresh);

        Cookie expiredRefreshCookie = new Cookie("refresh", null);
        expiredRefreshCookie.setMaxAge(0); // 즉시 만료
        expiredRefreshCookie.setPath("/"); // 기존의 경로와 동일하게 설정
        expiredRefreshCookie.setHttpOnly(true);
        expiredRefreshCookie.setSecure(true); // 기존과 동일한 보안 설정
        response.addCookie(expiredRefreshCookie);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 60*60 * 1000L); // 1시간
        boolean rememberMe = Boolean.parseBoolean(remember);
        long refreshTokenValidity = rememberMe ? 6 * 30 * 24 * 60 * 60 * 1000L : 24 * 60 * 60 * 1000L; // 6달 또는 5시간
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshTokenValidity);

        //response
        response.setHeader("Authorization", "Bearer " + newAccess);

        Cookie refreshCookie = cookieService.createCookie("refresh", refreshToken, refreshTokenValidity);
        refreshCookie.setPath("/");
        refreshCookie.setSecure(false);
        refreshCookie.setDomain("api.moaguide.com");
        response.addCookie(cookieService.createRememberMeCookie(rememberMe,refreshTokenValidity));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 리프레시 토큰 쿠키 만료
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

        return ResponseEntity.ok("Logged out successfully");
    }

}