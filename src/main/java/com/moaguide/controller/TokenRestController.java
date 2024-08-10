package com.moaguide.controller;


import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CookieService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenRestController {
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;

    @PostMapping("/refesh")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        String remember = null;
        Cookie[] cookies = request.getCookies();
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


        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 30 * 60 * 1000L); // 30분
        boolean rememberMe = Boolean.parseBoolean(remember);
        long refreshTokenValidity = rememberMe ? 6 * 30 * 24 * 60 * 60 * 1000L : 5 * 60 * 60 * 1000L; // 6달 또는 5시간
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshTokenValidity);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(cookieService.createCookie("refresh", refreshToken, refreshTokenValidity));
        response.addCookie(cookieService.createRememberMeCookie(rememberMe,refreshTokenValidity));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}