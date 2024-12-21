package com.moaguide.controller;

import com.moaguide.dto.ArticlePaywallRequestDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.ArticlePaywallService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticlePaywallController {

    private final ArticlePaywallService articlePaywallService;
    private final JWTUtil jwtUtil;

    /**
     * 아티클 저장 API
     *
     * @param jwt        JWT 토큰
     * @param requestDto 아티클 요청 DTO
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveArticle(
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestBody @Valid ArticlePaywallRequestDto requestDto) {

        Map<String, Object> response = new HashMap<>();
        try {
            // 1. JWT 유효성 검사
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                response.put("status", "error");
                response.put("message", "액세스 토큰이 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            String token = jwt.substring(7); // "Bearer " 제거
            if (jwtUtil.isExpired(token)) {
                response.put("status", "error");
                response.put("message", "액세스 토큰이 만료되었습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

//            // 2. 작성자 권한 확인
//            String role = jwtUtil.getRole(token);
//            if (!Role.ADMIN.toString().equals(role)) {
//                response.put("status", "error");
//                response.put("message", "접근 권한이 없습니다.");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
//            }

            // 3. 아티클 저장
            articlePaywallService.saveArticle(requestDto);

            response.put("status", "success");
            response.put("message", "아티클이 성공적으로 저장되었습니다.");
            return ResponseEntity.ok(response);

        } catch (JwtException e) {
            response.put("status", "error");
            response.put("message", "유효하지 않은 토큰입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "서버에러");
            // 에러 로그 출력
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

